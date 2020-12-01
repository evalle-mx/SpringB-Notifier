package net.tce.dto;

public class NotificationDto extends ComunDto implements Cloneable{

	private String idNotificacion;
	private String claveEvento;
	private String idPivote;
	private String tipoPivote;
	private Long idCandidato;
	private Long idPosibleCandidato;
	private String idEmisor;
	private String tipoEmisor;
	private String idPersonaReceptor;
	private Long idReceptor;
	private String nombreReceptor;
	private String listaMecanismoPasivo;
	private String listaMecanismoInmediato;
	private String vista;
	private String email;
	private String password;	
	private String tokenInicio;
	private String asuntoEmail;
	private String nombreEmpresa;
	private String nombrePosicion;
	private String nombreEmisor;
	private String comentario;
	private String comentario2;
	private String texto;
	private String contenido;
	private String hostName;
	private String hostAddress;
	private String fecha;
	private String nombreFase;
	private String nombreMonitor;
	private String nombreCandidato;
	private String rol;
	private String horaInicial;
	private String diaInicial;
	private String horaFinal;
	private String diaFinal;	
	private String nombre;
	  
	public NotificationDto(){}
	public NotificationDto(String idNotificacion){
		this.idNotificacion=idNotificacion;
	}
	
	/**
	 * 
	 * @param idPivote
	 * @param claveEvento
	 */
	public NotificationDto(Long idPivote,String claveEvento){
		this.idPivote=idPivote.toString();
		this.claveEvento=claveEvento;
	}
		
	/**
	 * 
	 * @param idPivote
	 * @param claveEvento
	 * @param comentario
	 */
	public NotificationDto(Long idPivote,String claveEvento, String comentario){
		this.idPivote=idPivote.toString();
		this.claveEvento=claveEvento;
		this.comentario=comentario;
	}
	
	/**
	 * 
	 * @param idPivote
	 * @param claveEvento
	 * @param comentario
	 */
	public NotificationDto(Long idPivote,String claveEvento, String nombrePosicion, boolean dummy){
		this.idPivote=idPivote.toString();
		this.claveEvento=claveEvento;
		this.nombrePosicion=nombrePosicion;
	}
	
	public NotificationDto(Long idPivote,String claveEvento,String nombrePosicion,String password, boolean dummy){
		this.idPivote=idPivote.toString();
		this.claveEvento=claveEvento;
		this.nombrePosicion=nombrePosicion;
		this.password=password;
	}
	
	/**
	 * 
	 * @param idPivote
	 * @param claveEvento
	 * @param comentario
	 */
	public NotificationDto(Long idPivote,String claveEvento, String comentario,
						   String nombreEmisor, String hostName, 
						   String hostAddress, String fecha){
		this.idPivote=idPivote.toString();
		this.claveEvento=claveEvento;
		this.comentario=comentario;
		this.nombreEmisor=nombreEmisor;
		this.hostName=hostName;
		this.hostAddress=hostAddress;
		this.setFecha(fecha);
	}
	
	
	/**
	 * Constructor usado por los metodos: 
	 *  NotificacionDaoImpl_getPeopleCandidateOfPosition(),
	 *  NotificacionDaoImpl_getPeople()
	 *  NotificacionDaoImpl_getPeoplePositionOfCandidateEnterprise()
	 *  NotificacionDaoImpl_getPositionPeopleOfPosition()
	 *  NotificacionDaoImpl_getPositionPeopleOfCandidate()
	 *  NotificacionDaoImpl_getPeopleByRelationType()
	 * @param idPersona
	 * @param email
	 * @param tokenInicio
	 * @param nombreReceptor
	 * @param idCandidato
	 */
	public NotificationDto(Long idPersona,String email,String tokenInicio,String nombreReceptor){
		this.idPersonaReceptor=idPersona.toString();
		this.email=email;
		this.tokenInicio=tokenInicio;
		this.nombreReceptor=nombreReceptor;
		
	}
	
	/**
	 * Constructor usado por los metodos:
	 *  NotificacionDaoImpl_getPositionPeopleAdmin()
	 * @param idPersona
	 * @param email
	 * @param tokenInicio
	 * @param nombreReceptor
	 * @param idCandidato
	 */
	public NotificationDto(Long idPersona,String email,String tokenInicio,String nombreReceptor, Long idCandidato){
		this.idPersonaReceptor=idPersona.toString();
		this.email=email;
		this.tokenInicio=tokenInicio;
		this.nombreReceptor=nombreReceptor;
		this.idCandidato=idCandidato;		
	}
	

	/**
	 * Constructor usado por los metodos:
	 *  NotificacionDaoImpl_getPersonOfPosibleCandidate()
	 * @param idPersona
	 * @param email
	 * @param tokenInicio
	 * @param nombreReceptor
	 * @param idPosibleCandidato
	 * @param dummy
	 */
	public NotificationDto(Long idPersona,String email,String tokenInicio,String nombreReceptor, 
															Long idPosibleCandidato,Boolean dummy){
		this.idPersonaReceptor=idPersona.toString();
		this.email=email;
		this.tokenInicio=tokenInicio;
		this.nombreReceptor=nombreReceptor;
		this.idPosibleCandidato=idPosibleCandidato;		
	}
	
	/**
	 * Constructor usado por los metodos:
	 *  NotificacionDaoImpl_getPeopleCandidateOfPositionPerson()
	 * @param idPersona
	 * @param email
	 * @param tokenInicio
	 * @param nombreReceptor
	 * @param nombrePosicion
	 */
	public NotificationDto(Long idPersona,String email,String tokenInicio,String nombreReceptor, String nombrePosicion){
		this.idPersonaReceptor=idPersona.toString();
		this.email=email;
		this.tokenInicio=tokenInicio;
		this.nombreReceptor=nombreReceptor;
		this.nombrePosicion=nombrePosicion;
	}
	
	public NotificationDto(Long idNotificacion,Long idEmisor,Byte tipoEmisor,String claveEvento, 
						   String texto, Boolean vista, String listaMecanismoPasivo){
		this.idNotificacion=idNotificacion.toString();
		this.idEmisor=idEmisor.toString();
		this.tipoEmisor=tipoEmisor.toString();
		this.claveEvento=claveEvento;
		this.texto=texto;
		this.listaMecanismoPasivo = listaMecanismoPasivo;
		this.vista=(vista ? "1":"0");
	}
	
	 public Object clone(){
	        Object obj=null;
	        try{
	            obj=super.clone();
	        }catch(CloneNotSupportedException ex){
	            System.out.println("No se puede duplicar el objeto NotificationDto");
	        }
	        return obj;
	    }	

	public String getIdNotificacion() {
		return idNotificacion;
	}
	public void setIdNotificacion(String idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getClaveEvento() {
		return claveEvento;
	}
	public void setClaveEvento(String claveEvento) {
		this.claveEvento = claveEvento;
	}
	
	public String getIdPersonaReceptor() {
		return idPersonaReceptor;
	}
	public void setIdPersonaReceptor(String idPersonaReceptor) {
		this.idPersonaReceptor = idPersonaReceptor;
	}

	public Long getIdReceptor() {
		return idReceptor;
	}
	public void setIdReceptor(Long idReceptor) {
		this.idReceptor = idReceptor;
	}
	public String getNombreReceptor() {
		return nombreReceptor;
	}
	public void setNombreReceptor(String nombreReceptor) {
		this.nombreReceptor = nombreReceptor;
	}
	public String getListaMecanismoPasivo() {
		return listaMecanismoPasivo;
	}
	public void setListaMecanismoPasivo(String listaMecanismoPasivo) {
		this.listaMecanismoPasivo = listaMecanismoPasivo;
	}
	public String getListaMecanismoInmediato() {
		return listaMecanismoInmediato;
	}
	public void setListaMecanismoInmediato(String listaMecanismoInmediato) {
		this.listaMecanismoInmediato = listaMecanismoInmediato;
	}
	public String getVista() {
		return vista;
	}
	public void setVista(String vista) {
		this.vista = vista;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTokenInicio() {
		return tokenInicio;
	}
	public void setTokenInicio(String tokenInicio) {
		this.tokenInicio = tokenInicio;
	}
	public String getIdEmisor() {
		return idEmisor;
	}
	public void setIdEmisor(String idEmisor) {
		this.idEmisor = idEmisor;
	}
	public String getTipoEmisor() {
		return tipoEmisor;
	}
	public void setTipoEmisor(String tipoEmisor) {
		this.tipoEmisor = tipoEmisor;
	}
	public String getAsuntoEmail() {
		return asuntoEmail;
	}
	public void setAsuntoEmail(String asuntoEmail) {
		this.asuntoEmail = asuntoEmail;
	}
	
	public String getNombrePosicion() {
		return nombrePosicion;
	}
	public void setNombrePosicion(String nombrePosicion) {
		this.nombrePosicion = nombrePosicion;
	}
	
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public String getNombreEmisor() {
		return nombreEmisor;
	}
	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}
	
	public String getIdPivote() {
		return idPivote;
	}
	public void setIdPivote(String idPivote) {
		this.idPivote = idPivote;
	}
	public Long getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(Long idCandidato) {
		this.idCandidato = idCandidato;
	}
	public String getTipoPivote() {
		return tipoPivote;
	}
	public void setTipoPivote(String tipoPivote) {
		this.tipoPivote = tipoPivote;
	}
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getIdPosibleCandidato() {
		return idPosibleCandidato;
	}
	public void setIdPosibleCandidato(Long idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}
	public String getNombreFase() {
		return nombreFase;
	}
	public void setNombreFase(String nombreFase) {
		this.nombreFase = nombreFase;
	}
	public String getNombreMonitor() {
		return nombreMonitor;
	}
	public void setNombreMonitor(String nombreMonitor) {
		this.nombreMonitor = nombreMonitor;
	}
	public String getNombreCandidato() {
		return nombreCandidato;
	}
	public void setNombreCandidato(String nombreCandidato) {
		this.nombreCandidato = nombreCandidato;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
		public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getComentario2() {
		return comentario2;
	}
	public void setComentario2(String comentario2) {
		this.comentario2 = comentario2;
	}
	public String getHoraInicial() {
		return horaInicial;
	}
	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}
	public String getDiaInicial() {
		return diaInicial;
	}
	public void setDiaInicial(String diaInicial) {
		this.diaInicial = diaInicial;
	}
	public String getHoraFinal() {
		return horaFinal;
	}
	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}
	public String getDiaFinal() {
		return diaFinal;
	}
	public void setDiaFinal(String diaFinal) {
		this.diaFinal = diaFinal;
	}
//	public String toString() {
//		return ReflectionToStringBuilder.toString(this);
//	}
}
