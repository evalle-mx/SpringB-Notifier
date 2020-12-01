package net.tce.util;

import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.log4j.Logger;

public class DateUtily {
	static Logger log4j = Logger.getLogger("net.tce.util.DateUtily");
	
	private static TimeZone TIMEZONE = TimeZone.getTimeZone("Mexico/General");
	public static Locale LOCALE = new Locale("es","MX");

	 /**
     * Comprueba si la fecha formado por los argumentos es válida.
     * @param a anio 
     * @param m mes
     * @param d día
     * @return cierto si la fecha es válida, esto es,
     */
	public static boolean esFechaValida(int anioBase,String anio, String mes, String dia) {	
		int inAnio=anio == null ? anioBase:Integer.parseInt(anio);
		int inMes=mes == null ? (getCalendar().get(Calendar.MONTH) + 1):Integer.parseInt(mes);
		int inDia=dia == null ? getCalendar().get(Calendar.DATE):Integer.parseInt(dia);
		//log4j.info("<esFechaValida> inAnio/inMes/inDia :" + inAnio + "/" + inMes + "/" + inDia);
        return isValidDate(String.valueOf(inDia).concat("/").concat(String.valueOf(inMes)).concat("/").
				 		   concat(String.valueOf(inAnio)), Constante.DATE_FORMAT_JAVA);
    }
    
	
	/**
     * Verifica que el parametro sea una fecha válida  
	 * @param sDate 
	 * @param pattern, i.e. dd/MM/yyyy
     * @return boolean
     */
    public static boolean isValidDate(String sDate,String pattern){
    	boolean resp=true;
    	try{
			SimpleDateFormat sdf=new SimpleDateFormat(pattern);
			sdf.setLenient(false);
			sdf.parse(sDate);
    		}catch(ParseException e){
    			resp=false;
    		}
    		log4j.debug(" &&  isValidDate() -> sDate:"+sDate+ " valido="+resp);
    		return resp;
    	}
	
	/**
	 * Metodo que compara dos fechas y  regresa
	 * 1: date1 > date2, -1: date1 < date2 y 0: equals
	 * @param date1
	 * @param date2 
	 * @return integer
	 */
	public static int compareDt1Dt2(Date date1, Date date2){
		return date1.compareTo(date2);
	}
	
	/**
	 * Regresa una fecha correspondiente a los parametros asignados
	 * @param anioBase, año propuesto
	 * @param anio, año
	 * @param mes, 0-11
	 * @param dia, 1-31
	 * @param hora, 1-60
	 * @param minuto, 1-60
	 * @param segundo,1-60
	 * @return  fecha correspondiente
	 */
	public static Date setDate(int anioBase, String anio, String mes, 
								String dia,String hora, String minuto,
								String segundo ){
		int inAnio=anio == null ? anioBase:Integer.parseInt(anio);
		int inMes=mes == null ? getCalendar().get(Calendar.MONTH)+1:Integer.parseInt(mes);
		int inDia=dia == null ? getCalendar().get(Calendar.DATE):Integer.parseInt(dia);
		log4j.debug(" setDate() -----------> "+inAnio+"/"+inMes+"/"+inDia+" "+hora+":"+minuto+" "+segundo);
		return setDateDirect(inAnio, inMes, inDia,Integer.parseInt(hora),
				Integer.parseInt(minuto),Integer.parseInt(segundo));
	}
	
	/**
	 * Regresa una fecha correspondiente a los parametros asignados
	 * @param anio, año
	 * @param mes, 0-11
	 * @param dia, 1-31
	 * @param hora, 1-24
	 * @param minuto, 1-60
	 * @param segundo,1-60
	 * @return  fecha correspondiente
	 */
	public static Date setDateDirect( int anio, int mes, 
			int dia,int hora, int minuto, int segundo ){
		Calendar cal = getCalendar();
		cal.clear();
		cal.set(anio, mes - 1, dia,hora,minuto,segundo);
		log4j.debug("setDateDirect() --> "+cal.getTime().getTime());
		return cal.getTime();
	}
	
	
	/**
	 * Regrea un objeto Calendar
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(){				 		
		 Calendar cal=Calendar.getInstance(TIMEZONE, Constante.LOCALE);
		 return cal;
	}
	
	
	
	/**
	 * returns the number of days Between 2 dates
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static long daysBetween(Date dIni, Date dFin){
		double days =lBetween(dIni, dFin, Constante.MULT_MILISEG_A_DIAS); 
	    return (new Double(days)).longValue();
	}
	/**
	 * Regresa fecha solicitada con patron asignado
	 * @param d (Date)
	 * @param pattern (String) i.e. dd/MM/yyyy
	 * @return String
	 */
	public static String thisDateFormated(Date d, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, LOCALE);
		return sdf.format(d);
	}
	
	/**
	 * Regresa la diferencia entre dos fechas
	 * @param dt1, primera fecha
	 * @param dt2, ultima fecha
	 * @param divider, este valor se le divide a la diferencia de las fechas
	 * @return
	 */
	public static double lBetween(Date dt1, Date dt2, long divider){
		double days = ((double)(dt2.getTime() - dt1.getTime()) / divider );
		return UtilsTCE.redondear(days, 0,RoundingMode.HALF_UP);
	}
	
	/**
	 * Regresa la fecha actual 
	 * @return Date
	 */
	public static Date getToday(){		
		return getCalendar().getTime();
	}
	
	
	/**
	 * Regresa la fecha actual con: Horas, minutos y segundos, en cero 
	 * @return Date
	 */
	public static Date getTodayZero(){		
		Calendar fechaCal =  getCalendar();
		fechaCal.set(Calendar.HOUR_OF_DAY, 0);
		fechaCal.set(Calendar.MINUTE, 0);
		fechaCal.set(Calendar.SECOND, 0);
		fechaCal.set(Calendar.MILLISECOND, 0);
		return fechaCal.getTime();
	}
	
	/**
	 * Regresa una fecha dada, en un string, dependiendo del formato
	 * @param fecha, fecha a convertir
	 * @param pattern, formato asigando
	 * @return la fecha en string
	 */
	public static String date2String(Date fecha, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		return sdf.format(fecha);
	}

	/**
	 * Convierte un string a fecha
	 * @param String
	 * @throws ParseException 
	 * @returns Date
	 * @throws Exception 
	 * @throws Exception 
	 */
	public static Date string2Date(String dateString, String pattern) throws ParseException {
		Calendar calendar = getCalendar();
		
			calendar.setTime(new SimpleDateFormat(pattern).parse(dateString));
		
		return calendar.getTime();
	}
	
	/**
	 * Regresa la edad de la fecha de nacimiento
	 * @param fecha de nacimiento
	 * @param format, es el formato de la  fecha
	 * @return
	 * @throws ParseException 
	 */
	public static Integer calcularEdad(String fecha, String format) throws ParseException {
	
		log4j.debug("%$%/ fecha"+fecha);
		Calendar birth = getCalendar();
		Calendar today =birth;
		Integer age=0;
		int factor=0;
		Date birthDate=new SimpleDateFormat(format).parse(fecha);
		Date currentDate=getToday(); //current date
		birth.setTime(birthDate);
		today.setTime(currentDate);
		if (today.get(Calendar.MONTH) <= birth.get(Calendar.MONTH)) {
			if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)) {	
				if (today.get(Calendar.DATE) > birth.get(Calendar.DATE)) {
					factor = -1; //Aun no celebra su cumpleaños
				}
			} else {
				factor = -1; //Aun no celebra su cumpleaños
			}
		}
		age=(today.get(Calendar.YEAR)-birth.get(Calendar.YEAR))+factor;
		return age;
		
	}
	
	/**
	 * Regresa la edad de la fecha de nacimiento
	 * @param fecha de nacimiento
	 * @return
	 */
	public static Integer calculaEdad(Date fechaNacimiento){
		Calendar birth =getCalendar();
		Calendar today =getCalendar();
		Integer age=0;
		int factor=0;
		birth.setTime(fechaNacimiento);
		today.setTime(getToday());
		
		if (today.get(Calendar.MONTH) <= birth.get(Calendar.MONTH)) {
			if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)) {	
				if (today.get(Calendar.DATE) > birth.get(Calendar.DATE)) {
					factor = -1; //Aun no celebra su cumpleaños
				}
			} else {
				factor = -1; //Aun no celebra su cumpleaños
			}
		}
		age=(today.get(Calendar.YEAR)-birth.get(Calendar.YEAR))+factor;
		return age;
		
	}
	
	/**
	 * Regresa una fecha en base a parametros de entrada, de manera que año debe ser cadena de 4 caracteres,<br>
	 * y dia puede ser null, tomandose por default el 1er dia del mes.<br>
	 * <b>Si alguno de los 3 parametros es invalido, regresa <i>null</i> </b>
	 * @param anio i.e. 1999
	 * @param mes i.e. 12
	 * @param dia i.e. 25
	 * @return Date <u>i.e.</u> Sat Dec 25 00:00:00 CST 1999
	 */
	public static Date creaFecha(String anio, String mes, String dia){
		Date fecha = null;
		if(anio!=null  && !anio.trim().equals("") 
			&& !anio.trim().equals("null") 
			&& anio.trim().length()==4 ){
			if(mes!=null  && !mes.trim().equals("")
				&& !mes.trim().equals("null")){
				if(dia == null  || dia.trim().equals("") || dia.equals("null")){
					dia="1"; /*Dia en fecha por default*/
				}				
				try{
					fecha = setDateDirect(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia), 0, 0, 0);
				}catch (Exception e) {
					log4j.error("<DateUtily> Error al convertir parametros en Enteros para procesar fecha ("+
							anio+"/"+mes+"/"+dia+")",e);
					fecha = null;
				}			
			}
		}
		
		return fecha;
	}
	
	/**
	 * Obtiene la fecha diferida entre el origen y los diasDIf
	 * @param fechaOrigen
	 * @param diasDif +d = fecha+dias | -d = fecha-dias
	 * @return
	 */
	public static Date getDateFrom(Date fechaOrigen, int diasDif){
		 Calendar cal=getCalendar();
		 cal.setTime(fechaOrigen);
		 cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)+diasDif);
		 return cal.getTime();
	}
	
	/**
	 * Se obtiene una cadena con los años, meses y dias, correspondientes a numero de dias dados
	 * @param totalDays, numero de dias
	 * @return totalDays > 0 se obtiene una cadena con los años, meses y dias
	 * 		   totalDays < 0 null
	 */
	public static String daysToYearsMonthDays(int totalDays){
		StringBuilder sb=null;
		if(totalDays > 0){
		 sb = new 	StringBuilder();
         int years = 0;
         int months = 0;

         // se asume los años de 365 dias
         years = (int)totalDays/365;
         totalDays %= 365;

         // se asume los meses de 30 dias
         months = (int)totalDays/30;
         totalDays %= 30;
         
         if (years > 0)
        	 sb.append(years).append(years == 1 ? " año":" años");
         if (months > 0)
        	 sb.append(", ").append(months).append(months == 1 ? " mes":" meses");
         if (totalDays > 0)
        	 sb.append(", ").append(totalDays).append(totalDays  == 1 ? " día":" dias");        
		}   
		return sb != null ?  sb.toString():null; 
	}
	
	 
    /**
	 * Suma las horas recibidos a la fecha 
	 * @param fecha, fecha a la que se le va asumar las horas
	 * @param horas, tiempo a sumar
	 * @return
	 */
	 public static Date addHours(Date fecha, int horas){	
		 Calendar calendar = getCalendar();
		 
		 // Configuramos la fecha que se recibe
		 calendar.setTime(fecha); 
		 
		 // numero de horas a añadir
		 calendar.add(Calendar.HOUR, horas);  
		 
		 return calendar.getTime();
	 }	 
	 
	 
	 /**
	 * Suma las horas recibidos a la fecha 
	 * @param fecha, fecha a la que se le va asumar las horas
	 * @param horas, tiempo a sumar
	 * @return
	 */
	 public static Date addMinutes(Date fecha, int minutes){	
		 Calendar calendar = getCalendar();
		 
		 // Configuramos la fecha que se recibe
		 calendar.setTime(fecha); 
		 
		 // numero de horas a añadir
		 calendar.add(Calendar.MINUTE, minutes);  
		 
		 return calendar.getTime();
	 }
	
	
	 /**
		 * se crea una lista de fechas en intervalos de minutos
		 * @param fechaInicio, fecha inicial de la lista
		 * @param fechafin, fecha final de la lista
		 * @param minutos, los minutos de cada intervalo
		 * @param horaInicio, la hora inicial de los intervalos
		 * @param horaFin,la hora final de los intervalos
		 * @return
		 */
		public static List<Date> partirIntervaloFechasMinutos(Date fechaInicio, Date fechafin,
				int minutos,int horaInicio,int horaFin ){
			List<Date> lsDate=new ArrayList<Date>();
			Calendar calendarFI =getCalendar();
			
			while(fechaInicio.getTime() < fechafin.getTime()){
				
				// Configuramos la fecha que se recibe
				calendarFI.setTime(fechaInicio); 
			
				if(horaInicio <= calendarFI.get(Calendar.HOUR_OF_DAY) &&
				   calendarFI.get(Calendar.HOUR_OF_DAY) < horaFin 	){
					lsDate.add(fechaInicio);	
					/*System.out.println("fecha:"+thisDateFormated(fechaInicio,"yyyy-MM-dd HH:mm:ss")+
						" hora:"+calendarFI.get(Calendar.HOUR_OF_DAY));*/
				}
						
				// numero de horas a añadir
				calendarFI.add(Calendar.MINUTE, minutos);  
				
				//se asigna la nueva fecha
				fechaInicio=calendarFI.getTime();
			}
			return lsDate;
		}
	 
	
   // public static void main(String[] args) {
    	
  
    	
   /* 	Date dateIni= setDate(2013,null ,"1","11","0","0","0");
    	System.out.println("fechaInicio: " + dateIni );*/
//    	Date dateFin= DateUtily.setDateDirect(2013 ,1,10,0,0,0);
//    	float diasInvertidos=(float) DateUtily.daysBetween(dateIni, dateFin);
//    	log4j.debug("diasInvertidos="+diasInvertidos);
    	
    	/*Short anioInicio = 1999;
    	Byte mes = 12;
    	Byte diaInicio = 25;
    	
    	
    	String stAnio = anioInicio==null?null:String.valueOf(anioInicio);
    	String stMes = mes==null?null:String.valueOf(mes);//error con null
    	String stDia = diaInicio==null?null:String.valueOf(diaInicio);
    	System.out.println(stDia);
    	
    	Date fechaInicio = creaFecha(stAnio, stMes, stDia );  //"2000", "12", "18" | stAnio,stMes,stDia
    	System.out.println("fechaInicio: " + fechaInicio );*/
    	/*
    	System.out.println("probando getDateFrom:");
    	try{
    		
    		Date origen = string2Date("28/02/2013", DEFAULT_PATTERN_DATE);
        	int diasDif = -30;
        	System.out.println("origen: " + date2String(origen, DEFAULT_PATTERN_DATE) + " menos " + diasDif +" dias" );
        	
        	Date nueva = getDateFrom(origen, diasDif);
        	System.out.println(nueva);
        	System.out.println(">" + date2String(nueva, DEFAULT_PATTERN_DATE));
    	}catch (Exception e){
    		e.printStackTrace();
    	}System.out.println("fin de getDateFrom");*/
    	
    	/*try {
    		Calendar calendar = Calendar.getInstance(); 
    		String mesFin = String.valueOf(calendar.get(Calendar.MONTH) + 1);
			String diaFin = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    		System.out.println(mesFin + "/" + diaFin );
    		
			Date dtInicio = new SimpleDateFormat("MM/dd/yyyy").parse("01/02/2000");
			Date dtFin = new SimpleDateFormat("MM/dd/yyyy").parse("18/09/2000");
			long diasOslen = (dtFin.getTime()-dtInicio.getTime())/(1000*60*60*24);
	    	System.out.println("diasOslen: " + diasOslen );
	    	long days = daysBetween(dtInicio, dtFin);
	    	System.out.println("days: " + days );
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
    	
   // }	
   
}
