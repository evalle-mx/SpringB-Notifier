package net.tce.util;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * 
 * @author Goyo
 *
 */
public abstract class Constante {

	/* Comunes 	 */
	//BOOLEANOS DE EVALUACION DE PARAMETROS REQUERIDOS DE INDICES EN ALTA DE CV MASIVO
	public final static Boolean MASIVE_IPG = new Boolean(true), MASIVE_IAS = new Boolean(true), MASIVE_IAP = new Boolean(true);
	public final static Byte SI_DIA_NULO = new Byte((byte) 1);
	public static Locale LOCALE = new Locale("es","MX");
	public final static String ID_EMPRESA_CONF_DUMMY="-1"; 
	public final static String ID_PERSONA_DUMMY="-1"; 
	public final static String DIVISOR_HTTP=" -HTTP- ";
	public final static String DIVISOR_ESTATUS=" status: ";
	public final static String OS = System.getProperty("os.name").toLowerCase();
	public final static byte MODO_ERROR_FATAL = 1;
	public final static boolean ACTIVO=true;
	public final static boolean INACTIVO=false;
	public final static String DELIMITADOR_AREAS_CONFIRM =",";
	public final static byte LENGHT_PASSWORD=8;
	public final static int MESSAGE_FATAL_LENGHT = 5000;
	public final static String BOL_TRUE_VAL = "1";
	public final static String BOL_FALSE_VAL = "0";
	
	/**
	 * EmpresaParametro:Publicacion unica
	 */
	public final static String ID_EMPRESA_DOTHR="1"; 
	/**
	 * Lo usa octavio
	 */
	public final static String IDCONF_NULL="N"; 
	/**
	 * EmpresaParametro:Publicación masiva(Create)
	 */
	
	// Rol
	public final static Byte ROL_ADMINISTRADOR=1;

	
	//HTTP 
	public final static int HTTP_ERROR_CLIENT_4xx_INI=400;
	public final static int HTTP_ERROR_CLIENT_4xx_FIN=431;	
		
	public final static String ID_EMPRESA_CONF_DEFAULT="1";
	public final static String ID_EMPRESA_CONF_MASIVO="2";//EmpresaParametro:Publicación masiva(Create)
	
	
	
	public final static String NO_PARCIAL="0";
	public final static String ACEPT_REST_JSON="Accept=application/json";
	public final static byte CLASIFICACION_CONTACTO_TEL_CEL=1;
	public final static byte CLASIFICACION_CONTACTO_LINK=2;
	public final static byte CLASIFICACION_CONTACTO_APP=3;
	public final static byte CLASIFICACION_CONTACTO_EMAIL=4;
	public final static byte CLASIFICACION_CONTACTO_CHAT=5;
	public final static int ANIO_NACIMIENTO_BASE_MIN=(DateUtily.getCalendar().get(Calendar.YEAR))-75;
    public final static int ANIO_NACIMIENTO_BASE_MAX=(DateUtily.getCalendar().get(Calendar.YEAR))-17;
	public final static int ANIO_PROGRAMACION_BASE_MIN=(DateUtily.getCalendar().get(Calendar.YEAR));
    public final static int ANIO_PROGRAMACION_BASE_MAX=(DateUtily.getCalendar().get(Calendar.YEAR))+3;
    public final static int ANIO_ACTUAL=(DateUtily.getCalendar().get(Calendar.YEAR));
	public final static byte PERSONA=1;
	public final static byte EMPRESA=2;
	public final static String AFIRMATIVO="si";
	public final static int T_DIAS_CADUCIDAD = 30;//DIAS PARA CONFIRMAR-CORREO (T) O ELIMINAR (2T) CUENTA INACTIVA.
	public final static byte MEXDF=9;
	public final static String MEXDFDESC="DISTRITO FEDERAL";
	public final static int EARTH_RADIUS=6371;
	
	//avisos
	public final static String AVISO_EMPTY_PER_TRAB="EMPTY_PER_TRAB";
	public final static String AVISO_EMPTY_DISP_HORA="EMPTY_DISP_HORA";
	public final static String AVISO_EMPTY_CAMB_DOM="EMPTY_CAMB_DOM";
	public final static String AVISO_EMPTY_EDO_CIVIL="EMPTY_EDO_CIVIL";
	public final static String AVISO_WARN_TIPO_JORNADA="WARN_TIPO_JORNADA";
	public final static String AVISO_EMPTY_TIPO_JORNADA="EMPTY_TIPO_JORNADA";
	
	//candidato
	public final static boolean CANDIDATO_NO_CLASIFICADO=false;

	// Estatus del candidato 
	public final static byte ESTATUS_CANDIDATO_ACEPTADO=1;
	public final static byte ESTATUS_CANDIDATO_RECHAZADO_SALARIO=2;
	public final static byte ESTATUS_CANDIDATO_RECHAZADO_DISTANCIA=3;
	public final static byte ESTATUS_CANDIDATO_RECHAZADO_DEMOGRAFICOS=4;
	public final static byte ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA=5;
	public final static byte ESTATUS_CANDIDATO_RECHAZADO_LABORAL=6;
	public final static byte ESTATUS_CANDIDATO_RECHAZADO_HABILIDADES=7;
	public final static byte ESTATUS_CANDIDATO_POR_CALCULAR=8;
	
	// Estatus operativo del candidato
	public final static Byte ESTATUS_CANDIDATO_OPERATIVO_INDEXADO=1;
	public final static Byte ESTATUS_CANDIDATO_OPERATIVO_VISTO=2;
	public final static Byte ESTATUS_CANDIDATO_OPERATIVO_INVITADO=3;
	public final static Byte ESTATUS_CANDIDATO_OPERATIVO_INACTIVO=4;
	public final static Byte ESTATUS_CANDIDATO_OPERATIVO_INTERESADO=5;
	public final static Byte ESTATUS_CANDIDATO_OPERATIVO_NO_INTERESADO=6;
	public final static Byte ESTATUS_CANDIDATO_OPERATIVO_EN_PROCESO=7;
	public final static Byte ESTATUS_CANDIDATO_OPERATIVO_DESCARTADO=8;
	public final static Byte ESTATUS_CANDIDATO_OPERATIVO_SELECCIONADO=9;

	// Estatus inscripcion
	public final static Byte ESTATUS_INSCRIPCION_CREADO=1;
	public final static Byte ESTATUS_INSCRIPCION_ACTIVO=2;
	public final static Byte ESTATUS_INSCRIPCION_PUBLICADO=3;
	public final static Byte ESTATUS_INSCRIPCION_INACTIVO=4;
	public final static Byte ESTATUS_INSCRIPCION_BORRADO=5;
	public final static Byte ESTATUS_INSCRIPCION_LISTA_NEGRA=6;
	public final static String EXPRESION_REGULAR_RFC_PERSONA = "^[A-Z]{4}[0-9]{6}[A-Z0-9]{3}$";

	// Estatus de la posicion
	public final static Byte ESTATUS_POSICION_CREADA=1;
	public final static Byte ESTATUS_POSICION_PUBLICADA=2;
	public final static Byte ESTATUS_POSICION_ERROR=3;
	public final static Byte ESTATUS_POSICION_DESACTIVADA=5;
	
	
	//Tipo Suscriptor
	public final static Byte TIPO_SUSCRIPTOR_PREMIUM=2;

	// empresa
//	public final static byte ID_RELACION_EMPRESA_PERSONA_DEFAULT=7;
	public final static String EXPRESION_REGULAR_RFC_EMPRESA = "^[A-Z]{3}[0-9]{6}[A-Z0-9]{3}$";
	public final static boolean ESTATUS_REGISTRO_ACTIVO = true;
	public final static String ESTATUS_REGISTRO_ACTIVO_S = "1";
	public final static boolean ESTATUS_REGISTRO_INACTIVO = false;
	public final static String ESTATUS_REGISTRO_INACTIVO_S = "0";
	
	// Tipo de perfil
	public final static byte TIPO_PERFIL_INTERNO=4;
	public final static String NOMBRE_PERFIL_INTERNO="Interno";
	
	// Rol
	public final static Byte ROL_CANDIDATO=3;
	
	/**
	 * Empresa Parametros BD
	 */
	//public final static String MAX_MIN_ANIO_EDAD="maxMinAnioEdad";
	//public final static String MAX_MIN_ANIO_ESCOLARIDAD="maxMinAnioEscolaridad";
	public final static String MAX_ANIOS_EXPERIENCIA="maxAniosExperiencia";
	public final static String QOS_APPLICANTS="qosApplicants";
	public final static String TIPO_PARAMETRO_4="4";
	public final static String TIPO_PARAMETRO_APLICACION_GENERAL="4";
	public final static String TIPO_PARAMETRO_ATRIBUTO_REQUERIDO="1";	
	public final static String TIPO_PARAMETRO_ATRIBUTO_REQUERIDO_POSICION="5";	
	public final static String TIPO_PARAMETRO_ATRIBUTO_REQUERIDO_EMPRESA="6";	
	public final static byte IDCONF_CVMASIVOS=1;
	public final static String MAX_ANIOS_EXPERIENCIA_DEFAULT="8";
	public final static String QOS_APPLICANTS_DEFAULT="10";
	public final static String CONTEXT_PARAM_TIME_CONFIRM_INSCRIP="timeToReminderConfirmInscrip";
	public final static String CONTEXT_PARAM_NUM_CONFIRM_INSCRIP="numToReminderConfirmInscrip";
	public final static String CONTEXT_PARAM_TIME_PUBLICATION="timeToReminderPublication";
	public final static String CONTEXT_PARAM_NUM_PUBLICATION="numToReminderPublication";
	public final static int CONTEXT_PARAM_TIME_CONFIRM_INSCRIP_DEFAULT=1440;//minutos
	public final static int CONTEXT_PARAM_NUM_CONFIRM_INSCRIP_DEFAULT=1;
	public final static int CONTEXT_PARAM_TIME_PUBLI_DEFAULT=1440;//minutos
	public final static int CONTEXT_PARAM_NUM_PUBLI_DEFAULT=1;
	public final static Byte TYPE_REMINDER_CONFIRM_INSCRIP=1;
	public final static Byte TYPE_REMINDER_PUBLICATION=2;
	
	
	/**
	 * Secuencias BD
	 */
	public final static String SECUENCIA_BD_AREA_PERSONA="SEQ_AREA_PERSONA";
	public final static String SECUENCIA_BD_AREA_PERFIL="SEQ_AREA_PERFIL";
	public final static String SECUENCIA_BD_AREA_EMPRESA="SEQ_AREA_EMPRESA";
	public final static String SECUENCIA_BD_AREA_TEXTO="SEQ_AREA_TEXTO";
	public final static String SECUENCIA_BD_SEQ_AVISO="SEQ_AVISO";
	public final static String SECUENCIA_BD_SEQ_AVISO_CAND="SEQ_AVISO_CANDIDATO";
	public final static String SECUENCIA_BD_PERSONA="SEQ_PERSONA";
	public final static String SECUENCIA_BD_HISTORICO_PASSWORD="SEQ_HISTORICO_PASSWORD";
	public final static String SECUENCIA_BD_CONTACT="SEQ_CONTACTO";
	public final static String SECUENCIA_BD_DOMICILIO="SEQ_DOMICILIO";
	public final static String SECUENCIA_BD_NOTIFICACION="SEQ_NOTIFICACION";	
	public final static String SECUENCIA_BD_TELEFONO="SEQ_TELEFONO";
	public final static String SECUENCIA_BD_PERSONA_PAIS="SEQ_PERSONA_PAIS";
	public final static String SECUENCIA_BD_EXPERIENCIA_LABORAL="SEQ_EXPERIENCIA_LABORAL";
	public final static String SECUENCIA_BD_TEXTO_NGRAM="SEQ_TEXTO_NGRAM";
	public final static String SECUENCIA_BD_HABILIDAD="SEQ_HABILIDAD";
	public final static String SECUENCIA_BD_REFERENCIA="SEQ_REFERENCIA";
	public final static String SECUENCIA_BD_ESCOLARIDAD="SEQ_ESCOLARIDAD";
	public final static String SECUENCIA_BD_TEXTO_CONOCIMIENTO="SEQ_TEXTO_CONOCIMIENTO";
	public final static String SECUENCIA_BD_CONTENIDO="SEQ_CONTENIDO";
	public final static String SECUENCIA_BD_CANDIDATO="SEQ_CANDIDATO";	
	public final static String SECUENCIA_BD_CONTROL_PROCESO="SEQ_CONTROL_PROCESO";	
	public final static String SECUENCIA_BD_PERSONA_PERFIL="SEQ_PERSONA_PERFIL";
	public final static String SECUENCIA_BD_PERMISO="SEQ_PERMISO";
	public final static String SECUENCIA_BD_DOCUMENTO_CLASIFICACION="SEQ_DOCUMENTO_CLASIFICACION";	
	public final static String SECUENCIA_BD_POSICION="SEQ_POSICION";
	public final static String SECUENCIA_BD_PERFIL_POSICION="SEQ_PERFIL_POSICION";
	public final static String SECUENCIA_BD_PERFIL_TEXTO_NGRAM="SEQ_PERFIL_TEXTO_NGRAM";
	public final static String SECUENCIA_BD_POLITICA_VALOR="SEQ_POLITICA_VALOR";
	public final static String SECUENCIA_BD_POLITICA_M_GENERO="SEQ_POLITICA_M_GENERO";
	public final static String SECUENCIA_BD_POLITICA_M_HABILIDAD="SEQ_POLITICA_M_HABILIDAD";
	public final static String SECUENCIA_BD_POLITICA_M_DISP_VIAJAR="SEQ_POLITICA_M_DISP_VIAJAR";
	public final static String SECUENCIA_BD_POLITICA_M_ESTADO_CIVIL="SEQ_POLITICA_M_ESTADO_CIVIL";
	public final static String SECUENCIA_BD_POLITICA_ESCOLARIDAD="SEQ_POLITICA_ESCOLARIDAD";
	public final static String SECUENCIA_BD_POLITICA_M_TIPO_JORNADA="SEQ_POLITICA_M_TIPO_JORNADA";
	public final static String SECUENCIA_BD_PERFIL="SEQ_PERFIL";
	public final static String SECUENCIA_BD_EMPRESA="SEQ_EMPRESA";
	public final static String SECUENCIA_BD_EMPRESA_PERFIL="SEQ_EMPRESA_PERFIL";
	public final static String SECUENCIA_BD_REL_EMPRPERSONA="SEQ_RELACION_EMPRESA_PERSONA";
	public final static String SECUENCIA_BD_CANDIDATO_RECHAZO="SEQ_CANDIDATO_RECHAZO";
	public final static String SECUENCIA_BD_AREA = "SEQ_AREA";
	public final static String SECUENCIA_BD_CORECLASS = "SEQ_CORE_CLASS";
	public final static String SECUENCIA_BD_PERSONAIDIOMA = "SEQ_PERSONAIDIOMA";//	seq_persona_idioma
	public final static String SECUENCIA_BD_POSICIONIDIOMA = "SEQ_POSICIONIDIOMA";//	seq_posicion_idioma
	public final static String SECUENCIA_BD_POLITICA_MVALOR = "SEQ_POLITICA_M_VALOR";
	public final static String SECUENCIA_BD_COMPETENCIA_PERFIL="SEQ_COMPETENCIA_PERFIL";
	public final static String SECUENCIA_BD_EMPRESA_PARAMETRO="SEQ_EMPRESA_PARAMETRO";
	public final static String SECUENCIA_BD_ROL_TAREA_PERMISO="SEQ_ROL_TAREA_PERMISO";
	public final static String SECUENCIA_BD_ROL="SEQ_ROL";
	public final static String SECUENCIA_BD_BONO="SEQ_BONO";
	public final static String SECUENCIA_BD_VALE="SEQ_VALE";
	public final static String SECUENCIA_BD_SEGURO="SEQ_SEGURO";
	public final static String SECUENCIA_BD_ENC_RESPUESTA="SEQ_ENC_RESPUESTA";
	public final static String SECUENCIA_BD_NOTIFICACION_PROGRAMADA="SEQ_NOTIFICACION_PROGRAMADA";
	public final static String SECUENCIA_BD_TAREA="SEQ_TAREA";
	public final static String SECUENCIA_BD_BTC_TRACK="seq_bitacora_track";
	public final static String SECUENCIA_BD_BTC_TRACK_CAND="seq_bitacora_track_cand";
	public final static String SECUENCIA_BD_BTC_TRACK_MON="seq_bitacora_track_mon";
	public final static String SECUENCIA_BD_BTC_TRACK_POST="seq_bitacora_track_post";
	public final static String SECUENCIA_BD_BTC_TRACK_CONT="seq_bitacora_track_cont";
	public final static String SECUENCIA_BD_BTC_TRACK_RECDRIO="seq_bitacora_track_recdrio";
	public final static String SECUENCIA_BD_BTC_MOD_RSC="seq_bitacora_modelorsc";
	public final static String SECUENCIA_BD_BTC_FASE="seq_bitacora_fase";
	public final static String SECUENCIA_BD_BTC_MONITOR="seq_bitacora_monitor";

	
	//bitacora posicion
	public final static String SECUENCIA_BD_BTC_COMPETENCIA_PERFIL="SEQ_BITACORA_COMPETENCIA_PERFIL";
	public final static String SECUENCIA_BD_BTC_DOMICILIO="SEQ_BITACORA_DOMICILIO";
	public final static String SECUENCIA_BD_BTC_PERFIL_TEXTO_NGRAM="SEQ_BITACORA_PERFIL_TEXTO_NGRAM";
	public final static String SECUENCIA_BD_BTC_POLITICA_VALOR="SEQ_BITACORA_POLITICA_VALOR";
	public final static String SECUENCIA_BD_BTC_POSICION="SEQ_BITACORA_POSICION";
	public final static String SECUENCIA_BD_BTC_TIPO_OPER_CREATE="1";
	public final static String SECUENCIA_BD_BTC_TIPO_OPER_UPDATE="2";
	public final static String SECUENCIA_BD_BTC_TIPO_OPER_DELETE="3";
	
	//tracking
	public final static String SECUENCIA_BD_TCK_MONITOR="SEQ_TRACKING_MONITOR";
	public final static String SECUENCIA_BD_TCK_POSTULANTE="SEQ_TRACKING_POSTULANTE";
	public final static String SECUENCIA_BD_MODELO_RSC="SEQ_MODELO_RSC";
	public final static String SECUENCIA_BD_MODELO_RSC_FASE="SEQ_MODELO_RSC_FASE";
	public final static String SECUENCIA_BD_MODELO_RSC_POSICION="SEQ_MODELO_RSC_POSICION";
	public final static String SECUENCIA_BD_MODELO_RSC_POSICION_FASE="SEQ_MODELO_RSC_POSICION_FASE";
	public final static String SECUENCIA_BD_MONITOR="SEQ_MONITOR";
	public final static String SECUENCIA_BD_TCK_BITACORA="SEQ_BITACORA_TRACKING";
	public final static String SECUENCIA_BD_POSIBLE_CANDIDATO="SEQ_POSIBLE_CANDIDATO";
	public final static String URI_TRACK_MODELO_RSC_POSICION = "/module/modeloRscPos";
	public final static String URI_TRACK_POSTULANTE= "/module/trackPostulante";
	public final static String URI_MONITOR = "/module/monitor";
	public final static String MODO_COMPLETO = "1";
	public final static String ES_POSIBLE_CANDIDATO_1 = "1";
	public final static String ES_POSIBLE_CANDIDATO_2 = "2";
	public final static String ES_POSIBLE_CANDIDATO_3 = "3";
	public final static int CASO_1 = 1;
	public final static Byte POSIBLE_CANDIDATO_2_1=1;
	public final static Byte POSIBLE_CANDIDATO_2_2= 2;
	public final static Byte POSIBLE_CANDIDATO_3=3;
	public final static Short NUM_ACTIVIDAD_INICIAL=1;
	public final static Short NUM_ORDEN_INICIAL=1;
	
	//bitacora tracking
	public final static String URI_BTC_TRACK_= "/module/btcTracking";
	public final static short TIPO_EVEN_BTC_CREATE=1;
	public final static short TIPO_EVEN_BTC_UPDATE=2;
	public final static short TIPO_EVEN_BTC_DELETE=3;
	public final static short TIPO_EVEN_BTC_DESCARTA=4;
	public final static short TIPO_EVEN_BTC_REINTEGRA=5;
	public final static short TIPO_EVEN_BTC_CONFIRM=6;
	public final static short TIPO_EVEN_BTC_ESCOGE=7;
	
	public final static short TIPO_MOD_BTC_MODRSC=1;
	public final static short TIPO_MOD_BTC_MODRSC_POS=2;
	public final static short TIPO_MOD_BTC_MODRSC_FASE=3;
	public final static short TIPO_MOD_BTC_MODRSC_POS_FASE=4;	
	public final static short TIPO_MOD_BTC_TRACK_MON=5;
	public final static short TIPO_MOD_BTC_TRACK_POS=6;
	public final static short TIPO_MOD_BTC_PRE_CAND=7;
	public final static short TIPO_MOD_BTC_CAND=8;
	public final static short TIPO_MOD_BTC_MONITOR=9;
	public final static short TIPO_MOD_BTC_TRACK_MON_RCTRIO=10;
	public final static short TIPO_MOD_BTC_TRACK_POS_RCTRIO=11;
	public final static short TIPO_MOD_BTC_TRACK_MON_CONT=12;
	public final static short TIPO_MOD_BTC_TRACK_POS_CONT=13;
	
	public final static String REASIG_TRACK_MON_ROL="En proceso avanzado, se reasigno la fase al monitor_rol:";

	//agenda
	public final static String SECUENCIA_BD_RECORDATORIO="SEQ_RECORDATORIO";
	
	//EstadoTracking
	public final static Byte EDO_TRACK_NO_CUMPLIDO=1;
	public final static Byte EDO_TRACK_EN_CURSO=2;
	public final static Byte EDO_TRACK_CUMPLIDO=3;
	public final static Byte EDO_TRACK_RECHAZADO= 4;
	
	/**
	 * DAO'S
	 */
	public final static String SQL_ORDERBY="orderby";
	public final static String SQL_KEY_SIMILARITY="key_similarity";
	public final static String SQL_VALUE_SIMILARITY="value_similarity";
	
	/**
	 * URI's
	 */
	//general CRUDGDG
	public final static String URI_UPDATE="/update";
	public final static String URI_MULTIPLE_UPDATE="/updmultiple";
	public final static String URI_RELOAD="/reload";
	public final static String URI_CREATE="/create";
	public final static String URI_CREATE_ALL="/createAll";
	public final static String URI_READ="/read";
	public final static String URI_DELETE="/delete";
	public final static String URI_GET="/get";
	public final static String URI_DATA_CONF="/dataconf";
	public final static String URI_EXISTS="/exists";
	public final static String URI_CLONE="/clone";
	public final static String URI_FIND_SIMILAR="/findSimilar";
	public final static String URI_PING="/ping";
	public final static String URI_ENABLE_EDITION="/enableEdition";
	public final static String URI_DISASSOCIATE="/disassociate";
	public final static String URI_CREATECOMPLETE = "/createcomplete";
	public final static String URI_CREATEMASIVE = "/createMasive";
	public final static String URI_CREATE_ASSOCIATION = "/requestAssociation";
	public final static String URI_UPD_ASSOCIATION = "/updateAssociation";
	public final static String URI_DISABLE="/disable";	
	public final static String URI_UPDATE_STATUS="/updatestatus";
	public final static String URI_GET_REQUESTS="/getRequests";
	public final static String URI_MERGE = "/merge"; //Usado en API (LinkedIn)
	public final static String URI_SET= "/set";
	public final static String URI_ROLL_BACK= "/rollback";
	public final static String URI_CONFIRM= "/confirm";
	
	/**
	 * Services
	 */	
	//Dataconf
	public final static String RESTRICTION_ALFA="alfa";
	public final static String RESTRICTION_ALFANUM="alfanum";
	public final static String RESTRICTION_NUMBERS="numbers";
	public final static String RESTRICTION_NEGATIVES="negatives";
	public final static String SIN_RESTRICTION_="sinRestriction";
	public final static String PATERRN_EMAIL="email";
	public final static String PATERRN_PASSWORD="password";
	public final static Byte PARAMETRO_DATACONF=8;
	
	//Filtros
	public final  static int F_CREATE = 1;
	public final static int F_UPDATE = 2;
	public final static int F_READ = 3;
	public final static int F_DELETE = 4;
	public final static int F_GET =5 ;
	public final static int F_DATACONF = 6;
	public final static int F_EXISTS = 7;
	public final static int F_CLONE = 8;
	public final static int F_ENABLE_EDITION = 9;
	public final static int F_SEARCH = 10;
	public final static int F_UPDATE_STATUS= 11;
	public final static int F_CREATECOMPLETE = 12;
	public final static int F_ROLL_BACK = 21;
	public final static int F_CONFIRM = 13;
	public final  static int F_CREATE_ALL = 14;
	
	//Service Bitacora Posicion
	public final static String URI_BTC_POSICION="/admin/btcPosicion";
	
	//Service Catalogue
	public final static String URI_CATALOGUE="/admin/catalogue";
	public final static String URI_CATALOGUE_GET="/getCatalogue";
	public final static String URI_CATALOGUE_GET_FILTER="/getCatalogueByFilter";
	public final static String URI_CATALOGUE_CREATE="/createCatalogueRecord";
	public final static String URI_CATALOGUE_UPDATE="/updateCatalogueRecord";
	public final static String URI_CATALOGUE_AREA="/getAreas"; //Uso particular de Documentos clasificados
	public final static String URI_CATALOGUE_GET_VALUES="/getCatalogueValues";

	//Service Settlement
	public final static String URI_SETTLEMENT="/module/settlement";
	
	//Service Contact
	public final static String URI_CONTACTO="/module/contact";
	
	//Service PersonSkill
	public final static String URI_PERSONA_HABILIDAD="/module/personSkill";
	
	//trackingMonitor
	public final static String URI_TRACK_MONITOR = "/module/trackMonitor";
	
		
	
	//Service CurriculumManagement
	public final static String URI_CURRICULUM="/module/curriculumManagement";
	public final static String MODO_EMAIL_CONFIRMACION="1";
	public final static Byte SIZE_CLAVE_ALEATORIA_MIN=15;
	public final static Byte SIZE_CLAVE_ALEATORIA_MAX=18;
	public final static Byte SIZE_ID_ENCRIPTADO=32;
//	public final static Byte DELETE_NOTIFICATION_ENVIO_POSICION=1;
	//public final static Byte DELETE_NOTIFICATION_ENVIO_CANDIDATO=2;
	
	//Service Curriculum Empresarial
	//public final static String URI_CURENTERPRISE="/module/curriculumEnterprise";
	public final static String URI_CURRICULUM_EMPRESA="/module/curriculumCompany";
	public final static String URI_CURRICULUM_EMPRESA_PUBLICATION="/setEnterpriseResumePublication";
	public final static String URI_REST_TASK="/module/task";
	public final static String URI_TEST="/test";

	public final static String MSV_CVS_EMP="EMPRESA_CVS";
	public final static String MSV_CVS="CVS";
	public final static String MSV_CVS_UBICACION="LOCALIZACIONES";
	public final static String MSV_CVS_EXPER="EXPERIENCIAS";
	public final static String MSV_CVS_ESCOLAR="ESCOLARIDADES";
	public final static String MSV_CVS_HABILIDAD="HABILIDADES";
	
	//Service password
	public final static String URI_PASSWORD="/module/password";
	
	//Service WorkExperience
	public final static String URI_WORK_EXPERIENCE="/module/workExperience";
		
	//Service textoNgram
	public final static String URI_TEXTO_NGRAM="/module/textNgram";
	
	//Service workReference
	public final static String URI_WORK_REFERENCE="/module/workReference";

	//Service academicBackground
	public final static String URI_ACADEMIC_BACKGROUND="/module/academicBackground";
		
	//Service LocationService
	public final static String URI_LOCATION="/module/location";
	public final static String TIPO_DOMICILIO_PRINCIPAL="1";
	
	//Service file
	public final static String URI_FILE="/module/file";
	public final static int FILE_SIZE_RANDOM=1000000;
	public final static byte FILE_SIZE_RANDOM_CADENA=3;
	public final static String FILE_DATE_FORMAT="yyyyMMdd";
	public final static String MODO_JSON="0";
	public final static String MODO_SOAP="1";
	public final static int BODY_SIN_ARCHIVO_BYTES=51;
	public final static String TIPO_CONTENIDO_FOTO="1";
	
	//Service LogOut
	public final static String URI_LOGOUT="/logout";
	
	//Service  EnterpriseParameters
	public final static String URI_ENTERPRISE="/module/enterpriseParameter";
	
	//Service Notify
	public final static String URI_NOTIFY_PROG="/module/notifyProg";

	// Service Applicant
	public final static String URI_APPLICANT="/module/applicant";
	public final static String URI_APPLICANT_GET_APPLICANTS="/getApplicants";
	public final static String URI_APPLICANT_SEARCH_APPLICANTS="/searchApplicants";
	public final static String URI_APPLICANT_SET_RESUME_PUBLICATION="/setResumePublication";
	//public final static String URI_APPLICANT_READ_VACANCY="/readVacancy";
	public final static int MULT_MILISEG_A_DIAS =(24 * 60 * 60 * 1000);
	public final static int MULT_MILISEG_A_SEGUNDOS =(1000);	
	public final static byte PROPOSITO_KO=1;
	public final static byte PROPOSITO_VALOR=2;
	public final static byte PROPOSITO_WARNING=3;
	public final static String TIPO_VALOR_UNICO="U";
	public final static String TIPO_VALOR_MULTI="M";
	public final static String TIPO_VALOR_RANGO="R";
	public final static String NM_POLITICA_VALOR_PERFIL="PF";
	public final static String NM_POLITICA_VALOR_POSICION="PS";
	public final static String NM_POLITICA_VALOR_EMPRESA="EP";
	public final static String NM_GRADO_ACADEMICO="GA";
	public final static String NM_ESTATUS_ESCOLAR="EE";
	public final static float MAX_SCORE_SIMILARITY = 0.9f;
	public final static String EXP_REG_ANALISIS_NGRAM_TOKEN=";|,| y | e | o | u ";
	public final static String EXP_REG_ANALISIS_NGRAM_ESPACIOS=EXP_REG_ANALISIS_NGRAM_TOKEN+"|\\s";
	public final static String EXP_REG_ANALISIS_NGRAM_TEXTO="^[0-9\\s_*+-]+$";
	public final static String FILTRO_STATUS="idEstatusCandidato";
	public final static short AMBITO_GEOGRAFICO_VALOR_ULTIMO=5;
	public final static byte DISPONIBILIDAD_VIAJAR_NO=3;
	
	// Service Social Api
	public final static String URI_MOD_LINKEDIN="/module/linkedin";
	
	/* ***********************************************************************  */

	// Claves de Politicas
	public final static String POLITICA_VALOR_EXP_LABORAL_KO="EXP_LABORAL_KO";
	public final static String POLITICA_VALOR_FORM_ACADEMICA_KO_MIN="FORM_ACADEMICA_KO_MIN";
	public final static String POLITICA_VALOR_FORM_ACADEMICA_KO_MAX="FORM_ACADEMICA_KO_MAX";
	public final static String POLITICA_VALOR_RANGO_EDAD_KO="RANGO_EDAD_KO";
	public final static String POLITICA_VALOR_SEXO_KO="SEXO_KO";
	public final static String POLITICA_VALOR_EDO_CIVIL_KO="EDO_CIVIL_KO";
	public final static String POLITICA_VALOR_PER_TRABAJO_KO="PERMISO_TRABAJO_KO";
	public final static String POLITICA_VALOR_DISP_HORARIO_KO="DISP_HORARIO_KO";
	public final static String POLITICA_VALOR_CAMBIO_DOM_KO="CAMBIO_DOMICILIO_KO";
//	public final static String CLAVE_POLITICA_HABILIDAD = "HABILIDAD_KO";
	public final static String CLAVE_POLITICA_IDIOMA = "IDIOMA_KO";
	public final static String CLAVE_POLITICA_CERTIFICACION = "CERTIFICACIONES";
	public final static short POLITICA_PONDERACION_DEFAULT=50;
	public final static short POLITICA_PONDERACION_TEXTO_DEFAULT=1;
	public final static Byte POLITICA_ID_ESTATUS_ESCOLAR_F_A_MIN = 30;
	public final static Byte POLITICA_ID_ESTATUS_ESCOLAR_F_A_MAX = 33;
	public final static String POLITICA_INDISTINTO = "0";
	public final static String POLITICA_VACIO = "";

	// Secciones de politicas
	public final static byte SECCION_DEMOGRAFICO = 5;
	
	// Conceptos de politicas
	public final static byte CONCEPTO_ESCOLARIDAD=10;
	public final static byte CONCEPTO_ESTADO_CIVIL=11;
	public final static byte CONCEPTO_TIPO_GENERO=13;
	public final static byte CONCEPTO_PERMISO_TRABAJO=14;
	public final static byte CONCEPTO_DISPONIBILDAD_HORARIO=15;
	public final static byte CONCEPTO_EDAD=16;
	public final static byte CONCEPTO_CAMBIO_DOMICILIO=17;
	public final static byte CONCEPTO_EXPERIENCIA_LABORAL=18;

	//Service VacancyService set
	public final static String URI_VACANCY="/module/vacancy";
	//public final static String URI_VACANCYTEXT="/module/vacancyText";
	public final static String URI_VACANCYACADEMICWEIGHING="/module/vacancyAcademicWeighing";
	public final static String URI_VACANCYPUBLICATION="/setVacancyPublication";

	//Service VacancyService set
	public final static String URI_AREATEXT="/module/areaText";
	public final static String URI_CREATEPRECLASSDOCS="/createPreclassifiedDocs";

	// Service Task (scheduler) 
	public final static String URI_TASK="/module/task";
	public final static String URI_TASK_SYNC_DOCS="/syncClassDocs";
	public final static String URI_TASK_RECLASSIFICATION="/reClassification";
	public final static String URI_TASK_REMODEL="/reModel";
	public final static String URI_TASK_SEARCHCANDIDATES="/searchCandidates";
	public final static String URI_TASK_RELOAD_CORE_SOLR="/reloadCoreSolr";
	public final static String URI_DOCS_CLASS="/module/classify";
	public final static String URI_DOCS_LOADTOKENS="/loadTokens";
	public final static String URI_DOCS_CLASSIFYBYLOT="/classifyByLot";

	
	
	//Service CheckAndRouteDocumentsTask
	public final static Short RECLASSIFICATION_AUTO=1;
	public final static int DEFAULT_NUM_DOCS_MODEL=0;
	public final static Byte TIPO_PROCESO_REMODEL_CLASS=1;
	public final static Byte TIPO_PROCESO_SYNC_DOCS_SOLR=2;
	public final static Byte TIPO_PROCESO_SYNC_DOCS_CONFIR=5;
	public final static Byte TIPO_PROCESO_RECLASS_DOCS=3;
	public final static Byte TIPO_PROCESO_RELOAD_CORE_SOLR=4;
	public final static String DEFAULT_NUM_LIMITE_CATEGORY="3";
	public final static String TIPO_SYNC_DOCS_SOLR="1";
	public final static String TIPO_SYNC_DOCS_CONFIR="2";
	public final static String TIPO_SYNC_DOCS_TODOS="3";
	public final static Byte NUM_LIMITE_QUERY_SOLR=110;


	//Service Notification set
	public final static String URI_NOTIFICATION="/module/notification";

	
	//Service HandShake set
	public final static String URI_HANDSHAKE ="/module/handshake";
	
	//llamada a servicios de transactional
	public final static String URI_ADMIN="/admin/management";
	public final static String URI_LASTDATE_SYNC_DOCS = "/lastDateSyncDocs";
	public final static String URI_LASTDATE_REMODEL = "/lastDateRemodel";
	public final static String URI_LASTDATE_RELOAD_CORE_SOLR = "/lastDateReLoadCoreSolr";
	public final static String URI_LASTDATE_RECLASS_DOCS = "/lastDateReclassDocs";
	
	//IAP
	public final static float PONDERACION_DEMOGRAFICOS=(float) 0.50;
	public final static float PONDERACION_EXPERIENCIA_LABORAL=(float) 0.50;
	public final static float PONDERACION_ESCOLARIDAD=(float) 0.50;
	//public final static float PONDERACION_HABILIDADES=(float) 0.33;
	public final static String PONDERACION_DEMOGRAFICOS_KEY="DEMOGRAFICO_PONDERA_IAP";
	public final static String PONDERACION_EXPERIENCIA_LABORAL_KEY="EXPERIENCIA_LAB_PONDERA_IAP";
	public final static String PONDERACION_ESCOLARIDAD_KEY="ESCOLARIDAD_PONDERA_IAP";
	public final static String PONDERACION_HABILIDADES_KEY="HABILIDAD_PONDERA_IAP";
	
	//Experiencia Laboral
	public final static short CADUCIDAD=1820;
	public final static short DURACION_VALIDA=180;
	public final static float CASTIGO=(float) 0.33;
	//public final static String FORMULA="(1)*A+(0.5)*B+(0.8)*C+(0.4)*D+(0.3)E+(0.1)F";
	public final static byte TIPO_JORNADA=1;
	public final static String NM_DURACION_VALIDA="DURACION_VALIDA";
	public final static String NM_CADUCIDAD="CADUCIDAD";
	public final static String NM_CASTIGO="CASTIGO";
	public final static String TRABAJO_ACTUAL="1";
	
	//Escolaridad
	public final static String GA_NIVEL="NIVEL";
	public final static String GA_DOMINIO="DOMINIO";
	public final static String GA_DEGRADACION="DEGRADACION";
	public final static String GA_DURACION="DURACION";
	public final static String EE_NIVEL="NIVEL";
	public final static String EE_PREPONDERANCIA="PREPONDERANCIA";
	public final static String FORM_ACADEMICA_KO_GA="FORM_ACADEMICA_KO_GA";
	public final static String FORM_ACADEMICA_KO_EE="FORM_ACADEMICA_KO_EE";
	public final static float CASTIGO_X_CADUCIDAD=(float) 0.15;
	public final static float ESCOLARIDAD_SUPERIOR=(float) 0.25;
	public final static float ESTATUS_INCOMPLETOS=(float) 0.25;
	public final static String NM_CASTIGO_X_CADUCIDAD="CASTIGO_X_CADUCIDAD";
	public final static String NM_ESCOLARIDAD_SUPERIOR="ESCOLARIDAD_SUPERIOR";
	public final static String NM_ESTATUS_INCOMPLETOS="ESTATUS_INCOMPLETOS";
	public final static String ESTATUS_ESCOLAR_ESTUDIANTE="3";

	//Domicilios
	public final static String TIPO_DOMICILIO_PRIMARIO="1";
	
	//PerfilTextoNgam
	public final static String ESTATUS_OPERATIVO_INACTIVO="0";
	
	//Service Indices
	public final static String EXP_REG_A="[àáâãäå]";
	public final static String EXP_REG_E="[èéêë]";
	public final static String EXP_REG_I="[ìíîï]";
	public final static String EXP_REG_O="[òóôõö]";
	public final static String EXP_REG_U="[ùúûü]";	
	public final static String EXP_REG_CARACTERES="[^0-9a-zA-Z ;,ñ]";
	public final static String EXP_REG_ESPACIO="\\s+";
	public final static String EXP_REG_ARTICULOS=" el | la | lo | las | los | un | una | unos | unas | al | del ";
	public final static String EXP_REG_CONJUNCION=" ni | bien | pero | sino | aunque |".
					concat(" que | ya que | puesto que | pues | porque | como ya |").
					concat(" si | siempre que | aunque | como | para | para que |").
					concat(" asi que | de modo que | de manera que | luego | por tanto |").
					concat(" cuando | mientras | tan pronto como | despues de que | hasta que ");
	// | y | e | o | u | no se quitan
	
	public final static String EXP_REG_ADVERBIO=" aqui | ahi | alli | alla | aca | arriba | abajo | cerca | lejos |".
					concat(" delante | encima | debajo | enfrente | atras | alrededor |").
					concat(" pronto | tarde | temprano | todavia | aun | ya | ayer | hoy |").
					concat(" mañana | siempre | nunca | jamas |").
					concat(" anoche | enseguida | ahora | mientras | anteriormente |").
					concat(" bien | mal | regular | despacio | deprisa | asi | aprisa |").
					concat(" adrede | peor | mejor | bastante | muy | mas | menos | algo |").
					concat(" demasiado | casi | solo | nada | tambien | cierto | claro |").
					concat(" exacto | obvio | no | tampoco | quiza | quizas | acaso | tal vez |").
					concat(" puede | puede ser | a lo mejor | solo | aun | inclusive | ademas |").
					concat(" incluso | viceversa | siquiera ");
	
	public final static String EXP_REG_PREPOSICIONES=" a | ante | bajo | cabes | con | contra |".
					 concat(" de | desde | durante | en | entre |").
					 concat(" excepto | hacia | hasta | mediante |").
					 concat(" pro | salvo | segun | sin |").
					 concat(" so | sobre | tras | via ");
	
	public final static String EXP_REG_PRONOMBRES=" yo | mi | me | conmigo | tu | ti | te | contigo | usted |".
					concat(" el | ella | ello | le | se | consigo | si |").
					concat(" nosotros | nosotras | nos | vosotros | vosotras | os |").
					concat(" ellos | ellas | les | se |").
					concat(" este | esta | esto | estos | estas |").
					concat(" ese | esa | eso | esos | esas |").
					concat(" aquel | aquella | aquello | aquellos | aquellas |").
					concat(" mio | tuyo | suyo | nuestro | vuestro |").
					concat(" mios | tuyos | suyos | nuestros | vuestros |").
					concat(" mia | tuya | suya | nuestra | vuestra |").
					concat(" mias | tuyas | suyas | nuestras | vuestras |").
					concat(" aquel | aquella | aquello | aquellos | aquellas |").
					concat(" ningun | ninguno | ninguna | nada | ningunos | ningunas |").
					concat(" uno | una | algun | alguno | alguna | alguna | algo | algunos | algunas |").
					concat(" ningun | ninguno | ninguna | nada | ningunos | ningunas |").
					concat(" poco | poca | pocos | pocas |").
					concat(" escaso | escasa | escasos | escasas |").
					concat(" mucho | mucha | muchos | muchas |").
					concat(" demasiado | demasiada | demasiados | demasiadas |").
					concat(" todo | toda | todos | todas |").
					concat(" mismo | misma | mismos | mismas |").
					concat(" tan | tanto | tanta | tantos | tantas |").
					concat(" varios | varias | alguien | nadie | cualquier |  cualquiera | cualesquiera |").
					concat(" quienquiera | quienesquiera | tal | tales | demás | bastante | bastantes |").
					concat(" que | cual | cuales | quien | quienes | cuyo | cuya | cuyos |  cuyas | donde |").
					concat(" cuanto | cuanta ");
	
	/**
	 * Catalogos
	 */
	public final static String CAT_PATH="net.tce.model.";
	public final static String CAT_PAIS="Pais";
	public final static String CAT_ESTADO="Estado";
	public final static String CAT_MUNICIPIO="Municipio";
	public final static String CAT_ASENTAMIENTO="Asentamiento";
	public final static String CAT_CIUDAD="Ciudad";
	public final static String CAT_EDO_CIVIL="EstadoCivil";
//	public final static String CAT_TIPO_TELEFONO="TipoTelefono";
	public final static String CAT_TIPO_ESTATUS_PADRES="TipoEstatusPadres";
	public final static String CAT_TIPO_CONVIVENCIA="TipoConvivencia";
	public final static String CAT_TIPO_VIVIENDA="TipoVivienda";
	public final static String CAT_AREA="Area";
	
	
	//Contiene la ubicacion de los pojos relacionados a catalogos
	@SuppressWarnings("serial")
	public final static	HashMap<String, String> hmCatalogos= new HashMap<String, String>(){
    	{
    		put(CAT_PAIS.toUpperCase(), CAT_PATH.concat(CAT_PAIS));
    		put(CAT_ESTADO.toUpperCase(), CAT_PATH.concat(CAT_ESTADO));
    		put(CAT_MUNICIPIO.toUpperCase(), CAT_PATH.concat(CAT_MUNICIPIO));
    		put(CAT_ASENTAMIENTO.toUpperCase(), CAT_PATH.concat(CAT_ASENTAMIENTO));
    		put(CAT_CIUDAD.toUpperCase(), CAT_PATH.concat(CAT_CIUDAD));
    		put(CAT_EDO_CIVIL.toUpperCase(), CAT_PATH.concat(CAT_EDO_CIVIL));
//    		put(CAT_TIPO_TELEFONO.toUpperCase(), CAT_PATH.concat(CAT_TIPO_TELEFONO));
    		put(CAT_TIPO_ESTATUS_PADRES.toUpperCase(), CAT_PATH.concat(CAT_TIPO_ESTATUS_PADRES));
    		put(CAT_TIPO_CONVIVENCIA.toUpperCase(), CAT_PATH.concat(CAT_TIPO_CONVIVENCIA));
    		put(CAT_TIPO_VIVIENDA.toUpperCase(), CAT_PATH.concat(CAT_TIPO_VIVIENDA));
    		put(CAT_AREA.toUpperCase(), CAT_PATH.concat(CAT_AREA)); 		
    	}
	};
	
	//Statistic methods
	public final static byte STAT_SD_METHOD = 1;
	public final static byte STAT_QUARTILE_METHOD = 2;
	public final static byte STAT_AUTO_METHOD = 3;
	
	//Manejo de fechas
	
	public final static String DATE_FORMAT = "yyyy-MM-dd";	
	public final static String DATE_FORMAT_BD = DATE_FORMAT+"  HH24:MI:SS";	
	public final static String DATE_FORMAT_JAVA = DATE_FORMAT+" HH:mm:ss";
	public final static String DATE_FORMAT_JAVA_ZONE = DATE_FORMAT+" HH:mm:ss zzz";
	public final static String HOUR_FORMAT1="HH:mm 'hrs'";
	public final static String DATE_FORMAT1="EEEEE dd 'de' MMMMM 'de' yyyy";
	
	// Servicios operativos
	public final static  byte SOLR_CORE=1;
	public final static String DEFAULT_QUERY_MODEL_SOLR= "(classified:3 AND modelversion:?) OR classified:4";
	public final static String DEFAULT_QUERY_RECLASSIFIED_SOLR="classified:(0 OR 1 OR 2) AND modelversion:?";
	public final static String DEFAULT_QUERY_SYNCHRONIZE_SOLR= "classified:0 OR classified:1 OR classified:2";
	public final static String OPERATIVE_CREATEMODEL_ENDPOINT = "/module/classifier/createModel";	
	public final static String OPERATIVE_SEED_CLASSIFIER = "/module/classifier/seedClassifier";
	public final static String OPERATIVE_SAVECLASSDOC_ENDPOINT = "/module/solrDoc/saveClassDocument";	
	public final static String OPERATIVE_UPDATECLASSSTATUS_ENDPOINT = "/module/solrDoc/updateClassStatus";	
	public final static String OPERATIVE_SEARCHCLASSBYQUERY_ENDPOINT = "/module/solrDoc/searchClassByQuery";
	public final static String OPERATIVE_COUNTBYQUERY_ENDPOINT = "/module/solrDoc/countByQuery";
	public final static String OPERATIVE_BULKUPDATE_ENDPOINT = "/module/solrDoc/bulkUpdate";	
	public final static String OPERATIVE_RELOAD_ENDPOINT = "/module/solrCore/reloadcore";	
	public final static String OPERATIVE_DELETECLASSDOC_ENDPOINT = "/module/solrDoc/deleteClassByQuery";
	public final static Byte PROCESS_STATUS_IN_PROGRESS = 1;	
	public final static Byte PROCESS_STATUS_CLOSED = 2;
	public final static Byte PROCESS_STATUS_CANCEL = 3;
	public final static Byte PROCESS_STATUS_ERROR = 4;	
	public final static int CLASSIFICATION_STATUS_UNCLASSIFIED = 0;	
	public final static int CLASSIFICATION_STATUS_PARTIAL_CLASSIFIED = 1;	
	public final static int CLASSIFICATION_STATUS_CLASSIFIED = 2;	
	public final static int ESTATUS_CLAS_VERIFICADO_MODELO = 3;	
	public final static int ESTATUS_CLAS_VERIFICADO_NOMODELO = 5;
	public final static int CLASSIFICATION_STATUS_PRECLASSIFIED = 4;	
	public final static String CONTEXT_PARAM_FOR_MODEL="queryForModel";	
	public final static String CONTEXT_PARAM_FOR_RECLASSIFIED="queryForReClassified";
	public final static String CONTEXT_PARAM_FOR_SYNCHRONIZE="queryForSynchronize";
	public final static String CONTEXT_PARAM_NUMDOCS_FOR_MODEL="numDocsForModel";	
	public final static String CONTEXT_PARAM_RECLASSIFICATION_AUTO="reclassificationAuto";
	public final static String CONTEXT_PARAM_NUM_LIMITE_CATEGORY="numeroLimiteCategory";
	public final static long DOCTYPE_CV = 1;	
	public final static long DOCTYPE_PERFIL = 2;	
	public final static long DOCTYPE_EMPRESA = 3;	
	public final static int DEFAULT_SEARCH_TIME_BY_POSITION = 3600;	
	public final static String SOLR_CONTENTSEPARATOR = " ";
	public final static String CLASSIFICATION_STATUS_IN_CLAUSE = "3,5";//Se emplea: where estatusClasificacion in (?) 
	public final static String PARAM_SOLR_MV="modelversion";
	public final static String PARAM_SOLR_COMODIN="?";
	
	//end-point transactional
	public final static String URI_DATA_CONF_MASIVE="/dataconfmasive";
	

	
	// Servicios REST
	public final static String ERROR_CODE_SYSTEM = "000";//Existi\u00F3 un error en la aplicaci\u00F3n (props)
	public final static String CHARSET=";charset=UTF-8";
	public final static String ERROR_LABEL_SERVICE = "Error en el servicio ";

	//Tipo relacion persona
	public final static int EMPRESA_TIPO_RELACION_EMPLEADO = 1;
	public final static int EMPRESA_TIPO_RELACION_SOLICITANTE = 8;
	public final static int EMPRESA_TIPO_RELACION_ADMINISTRADOR = 9;
	
	//Relacion persona_empresa
	public final static Boolean RELACION_EMPRESA_PERSONA_REPRESENTANTE=true;

	//Parámetros generales
	public final static String PARAM_GENERAL_CURRRENTMODEL = "currentModel";
	public final static String PARAM_GENERAL_PREVIOUSMODEL = "previousModel";
	
	/* ARGUMENTOS PARA FUNCIONALIDAD EN PostgreSQL */
	
	public final static String PSG_TOCHAR_BIGINT = "'FM9999999999999999999'";
	public final static String PSG_TOCHAR_DATE = "'"+DATE_FORMAT_BD+"'";
	public final static String PSG_TOCHAR_COUNT = "'FM9999'";
	
	public final static String DB_MANAGER_ORACLE = "ORA";
	public final static String DB_MANAGER_PSG = "PSG";
	public static final String EMAIL_DEF_SENDER = "reclutamientoTalentwise@dothr.net";
	public static final String EMAIL_FAIL_SENDER = "soporte@dothr.net";	
}
