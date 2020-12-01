package net.tce.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import net.tce.dao.AbstractDao;
import net.tce.dao.PersistenceGenericDao;
import net.tce.util.Constante;
import net.tce.util.DBInterpreter;
import net.tce.util.UtilsTCE;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 * La implementación en clase asbtracta para todas las operaciones comunes de los DAO's
 * @author Goyo
 * @param <T>, el POJO correspondiente
 * @param <PK>, filtros
 */
@SuppressWarnings("unchecked")
@Repository("persistenceGenericDaoImpl")
@Transactional
public  class PersistenceGenericDaoImpl<T, PK >
		extends AbstractDao implements PersistenceGenericDao<T, PK> {
	
	private Class<T> type = null;
	Logger log4j = Logger.getLogger( this.getClass());
	
	@Inject
	DBInterpreter dbInterpreter;

	 /**
     * Persiste un objeto en la base de datos.
     * @param newInstance, nuevo objeto
     */
	 
	public Object create(T newInstance) {		
		return this.getSession().save(newInstance);	
	}
	/**
     *Guarda los cambios hechos al objeto persistente.
	 *@param T, objeto a actualizar
	 */
	
	public void update(T transientObject) {
		this.getSession().update(transientObject);
	}
	
	/**
     *Guarda los cambios hechos al objeto persistente o lo crea.
	 *@param T, objeto a actualizar
	 */
	
	public void saveOrUpdate(T transientObject) {
		this.getSession().saveOrUpdate(transientObject);
		
	}
	
	/**
     * Remueve el objeto de la base de datos.
	 *@param T, objeto a borrar
	 */
	
	public void delete(T persistentObject) {
		this.getSession().delete(persistentObject);		
	}

	/**
	 * Aplica la operacion merge en la sesion
	 *  @param persistentObject
	 * @return 
	 */
	
	public Object merge(T persistentObject){
		return this.getSession().merge(persistentObject);
	}
	
	
	public  void flush(){
		this.getSession().flush();
	}
	
	
	/**
	 * Obtener una lista de objetos en la bd
	 * @return List<T>, lista de objetos
	 */
	@Transactional(readOnly=true)
	public List<T> findAll() {
		return (List<T>)this.getSession().createCriteria(getType()).list();
	}
	
	/**
	 * Obtiene por reflexion el tipo del DomainObject que usa este DAO
	 * @return the type
	 */
	@SuppressWarnings("rawtypes")
	public Class<T> getType() {
		if (type == null) {
			Class clazz = getClass();
			while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
				clazz = clazz.getSuperclass();
			}
			type = (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass())
					.getActualTypeArguments()[0];
		}
		return type;
	}

	/**
     * Recupera un objeto usando el id indicado como llave primaria.
     * @param id, llave primaria
     * @return el objeto correspondiente
     */
	@Transactional(readOnly=true)
	public T read(PK id) {
		Criteria criteria = this.getSession().createCriteria(getType());	
		criteria.add(Restrictions.idEq(id));
		List<T> lst=criteria.list();
		if(lst == null || lst.size() == 0)
			return null;
		else
			return lst.get(0);
	}

	 /**
     * Recupera una lista de objetos usando varios filtros.
     * @param filtros, un mapa con los filtros a aplicar
     * @return una lista de objetos correspondientes
	 * @throws Exception 
     */
	@Transactional(readOnly=true)
	public List<T> getByFilters(PK filtros) throws Exception{
		Criteria criteria = this.getSession().createCriteria(getType());	
		HashMap <String, Object> htFilters=(HashMap<String, Object>)filtros;
		Iterator <String> e =  htFilters.keySet().iterator();
		String key;
		Object valor;
		String key_similarity=null;
		String value_similarity=null;
        while( e.hasNext() ){ 
        	key=e.next();
        	valor=htFilters.get(key);   
        	if(valor != null){
	        	if(key.equals(Constante.SQL_ORDERBY) ){
	        		Iterator<String> itOrder= ((List<String>)valor).iterator();
	        		while(itOrder.hasNext()){        			
	        			criteria.addOrder(Order.asc(itOrder.next()));  
	        		}        		      	
	        	}else if(key.equals(Constante.SQL_KEY_SIMILARITY)){
	        		key_similarity=(String)valor;
	        	}else if(key.equals(Constante.SQL_VALUE_SIMILARITY)){
	        		value_similarity=(String)valor;
	        	}else{
	        		criteria.add(Restrictions.eq(key, valor)); 
	        	}  
        	}else{
        		criteria.add(Restrictions.isNull(key)); 
        	}
        }
        List<T> lsObjects=(List<T>)criteria.list();
        //Se analiza la similaridad de los resultados con respecto a una cadena dada
        if(key_similarity != null && value_similarity != null && lsObjects != null){
        	String cadena;
        	float resp;
        	Iterator<T> itObject=lsObjects.iterator();
        	while(itObject.hasNext()){
        		Object object=itObject.next();
        		cadena=(String)UtilsTCE.executeReflexion(object, key_similarity,null,null,null);
        		if(cadena != null){
        			//Se analiza la similitd
        			resp=UtilsTCE.similarity(UtilsTCE.replaceEspecialChars(cadena).toUpperCase(), 
								UtilsTCE.replaceEspecialChars(value_similarity).toUpperCase());
        			//No hay similitud
        			if(resp ==  0.0f){
        				itObject.remove();
        			}else{
        				  log4j.debug("similitud con: "+value_similarity+" ponderacion:"+resp);
        				//Se adiciona la ponderacion de similitud, al objeto
        				UtilsTCE.executeReflexion(object,"sortWeigh",resp,null,null);
        			}
        		}else{
        			itObject.remove();
        		}
        	}
        }   
       // log4j.debug("getByFilters --> lsObjects2="+lsObjects.size());            
        return lsObjects;
        
	 }
	
	 /**
     * Recupera una lista de objetos ejecutando la operaci�n between(SQL)
     * @param filtros, 
     * @return una lista de objetos correspondientes
     */
	@Transactional(readOnly=true)
	public List<T> getByBetweenFilters(PK filtros){
		Criteria criteria = this.getSession().createCriteria(getType());	
		Hashtable<String, Object> htFilters=(Hashtable<String, Object>)filtros;
		Enumeration<String> e = htFilters.keys();
		String key;
		List<Object> valores;
        while( e. hasMoreElements() ){ 
        	key=e.nextElement();
        	valores=(List<Object>)htFilters.get(key);        	
        	criteria.add(Restrictions.between(key, valores.get(0), valores.get(1)));
        }
        return (List<T>)criteria.list();
	}
	
	/**
     * Recupera una lista de objetos dependiendo de la sentencia HQL 
     * @param hql, la sentencia HQL
     * @return lista de objetos correspondientes
     */
	@Transactional(readOnly=true)
	public List<T> getByQuery(PK hql){
		String htSql = (String)hql;
		Query query = this.getSession().createQuery(htSql);		
		return (List<T>)query.list();
	}
		
	/**
     * Obtiene el siguiente valor de la secuencia dada
     * @param nombreSeq, nombre de la secuencia
     * @return la secuencia correspondiente
     */	
	public BigDecimal generaSecuencia(final PK nombreSeq){		
//		log4j.debug("<generaSecuencia>");
		BigDecimal bdecimal = null;
		Object uResult = getSession().createSQLQuery(dbInterpreter.getSecuencia((String) nombreSeq)
													.toString()).uniqueResult();
		log4j.debug("<generaSecuencia> uResult " + uResult );
		if(uResult instanceof java.math.BigInteger){
//			log4j.debug("<generaSecuencia> 1 ");
			bdecimal = new BigDecimal( (java.math.BigInteger) uResult );
		}else{
//			log4j.debug("<generaSecuencia> 2 ");
			bdecimal = (BigDecimal) uResult;
		}
		return bdecimal;			
	}
	
	/**
     * Recupera una lista de objetos dependiendo de la sentencia SQL 
     * @param hql, la sentencia SQL
     * @return lista de objetos correspondientes
     */
	@Transactional(readOnly=true)
	public List<T> getBySQLQuery(PK sql){
		SQLQuery query = this.getSession().createSQLQuery((String)sql);			
		return (List<T>)query.list();
	}
	
	
	 /**
     * Borra el registro de la persona en la tabla dada
     * @param filtros, es un objeto map que contiene el idPersona y el nombre de la tabla
     */
	public void deleteByPersona(PK id) {
		log4j.debug("<deleteByPersona> Type["+getType().getName()+"] id="+id);
		this.getSession().createQuery(new StringBuilder("delete from ").
				append(getType().getName()).
				append(" where  persona.idPersona = :idPersona ").toString()).
				setLong("idPersona",(Long)id).executeUpdate();
		
	}
	
	 /**
     * Borra el registro de la empresa en la tabla dada
     * @param filtros, es un objeto map que contiene el idEmpresa y el nombre de la tabla
     */
	public void deleteByEmpresa(PK id) {
		log4j.debug("<deleteByEmpresa> Type["+getType().getName()+"] id="+id);
		this.getSession().createQuery(new StringBuilder("delete from ").
				append(getType().getName()).
				append(" where  empresa.idEmpresa = :idEmpresa ").toString()).
				setLong("idEmpresa",(Long)id).executeUpdate();
	}


}
