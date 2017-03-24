/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantcopia2;

import java.util.Scanner;
import java.sql.*;

public class RestaurantCopia2 {
    
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:restaurant";
    static final String USER = "postgres";
    static final String PASS = "root";
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
      
        boolean bOP = true;//Bucle Menú Principal
        boolean bIniciar = true;//Bucle Iniciar Sessio
        
        
        boolean bAdmin = true;//Bucle Menú administrador
        boolean bReserva = true;//Cancel·lar Sol·licitud Reserva
        boolean bModificarRes;//Bucle Menú Modificar Reserva
        
        
        
        String[][] sTUsuari = new String[99][4];//Base de dades usuari
               
        
        
        
        String nTelefonReserva = "Introdueixi el numero de telefon amb el que va fer la reserva:\n -> "; //Text demanar Telefon reserva(Visualitzat/Modificar)
        
        String sMeses[] = {"Gener","Febrer","Març","Abril","Maig","Juny","Juliol","Agost","Septembre","Octubre","Novembre","Desembre"};
        

        //*Reservar espacio de clases*//   
        TReserva reserva[] = new TReserva[1000];
        Menus Menus = new Menus();
        Variables var = new Variables();
        Errores err = new Errores();
        BaseDades db = new BaseDades();
        Usuari usu = new Usuari();
        
        //********** Reserva espai array class **********//
        for(int i=0; i<1000; i++)reserva[i] = new TReserva();
        
        for(int i  = 0; i<100; i++){
            reserva[i].iTelefon = 0;
        }
        //********** Reserva espai array class **********//
        
        while(bOP == true){
            
            Menus(Menus.MenuPrincipal);
            var.iOpcioPrincipal = iLlegirOpcioMenu();
            
            //Comprovar si l'usuari vol tancar el programa.
            if(var.iOpcioPrincipal == 4){
                System.out.println("\033[34m"+"Adeu!");
                break;
            }
            
            switch(var.iOpcioPrincipal){
                //*********************************************************************************INICI SESSIÓ*//
                case 1:{ //
                    
                    while(bIniciar == true){
                        System.out.println("----------------\n INICIAR SESSIÓ \n----------------");
                        
                        System.out.println("Escriu 'adeu' per tornar al menu");
                        //Demanar i guardar nom d'usuari
                        var.sUsuari = sLlegirText(var.fUsuari);
                        //Comprovar si l'usuari vol cancel·lar l'inici de sessio
                        if(var.sUsuari.equals("adeu")){
                            System.out.println("\033[33m" + "Inici de sessió cancel·lat" + "\033[30m");
                            break;
                        }
                        //Demanar i guardar contrasenya
                        var.sContrasenya = sLlegirText(var.fContrasenya);
                        
                        //Comprovar usuari
                        ComprovarUsuari(var, db, usu);
                        
                        //Mostra missatge depenent si l'usuari s'ha connectat correctament o no
                        if(var.bUsuariC == false){
                            System.out.println("\033[47m" + "\033[31m" + "Usuari o contrasenya incorrecte" + "\033[30m");
                        }else{
                            System.out.println("\033[47m" + "\033[32m" + "S'ha iniciat la sessió correctament" + "\033[30m");
                            
                            //Comprova si l'usuari es un usuari client
                            if(usu.tipus.equals("usu")){
                                do{    
                                    
                                    //Menu principal
                                    Menus(Menus.MenuReserves);
                                    var.iOpcioSolicitud = iLlegirOpcioMenu();
                    
                                    switch(var.iOpcioSolicitud){
                                        case 1:{ //SOLICITAR RESERVA*******************************************************************************/
                                            SolicitarReserva(var, db, Menus, sMeses, reserva);
                                            break;
                                        } 
                                        case 2:{ //*************************************************************CANCELAR RESERVA*//
                                            CancelarReserva(reserva, sMeses, var, err, Menus);
                                            break;
                                        }
                                        
                                        case 3:{ //*************************************************************BUSCAR RESERVA*//
                                            BuscarReserva(reserva, sMeses, var, err, Menus);
                                            break;
                                        }
                                        
                                        case 4:{ //*************************************************************MODIFICAR RESERVA*//
                                            
                                            bModificarRes = true;
                                            
                                            while(bModificarRes == true){
                                                
                                                Menus(Menus.MenuModificarReserva);   
                                                var.iTelefon = sLlegirNumero(nTelefonReserva);

                                                if(var.iTelefon == 13){
                                                    System.out.println("\033[33m" + "Modificacio de resrva cancel·lada" + "\033[30m");
                                                    break;
                                                }
                                                
                                                for(int i = 0; i < var.c; i++){
                                                    if(var.iTelefon==reserva[i].iTelefon){
                                                        System.out.println("---------------\nDADES DE LA RESERVA\n---------------\n"
                                                            + "Nom: "+reserva[i].sNomReserva+"\n"
                                                            + "Nº Comensals: "+reserva[i].iComensalsR+"\n"
                                                            + "Dia: "+reserva[i].iDiaReserva+"\n"
                                                            + "Mes: "+sMeses[reserva[i].iMesReserva-1]+"\n"
                                                            + "Telefon y numero de registre: "+reserva[i].iTelefon);
                                                        
                                                        Menus(Menus.MenuOpcionsModificarReserva);
                                                        var.iOpcioModificar = iLlegirOpcioMenu();
                            
                                                        switch(var.iOpcioModificar){
                                                            case 1:{ //MODIFICAR NOM
                                                                System.out.println("Nom actual "+ reserva[i].sNomReserva);
                                                                
                                                                var.sNomReserva = sLlegirText(var.fNouUsuari);
                                                                
                                                                System.out.println("Nom modificat a " + var.sNomReserva);
                                                                reserva[i].sNomReserva = var.sNomReserva;
                                                                break;
                                                            }
                                                            case 2:{ //MODIFICAR COMENSALS
                                                                while(true){
                                                                    System.out.println("Nº actual de comensals: "+reserva[i].iComensalsR);
                                                                    var.iComensalsR = sLlegirNumero(var.nComensalsReserva);
                        
                                                                    if(var.iComensalsR <= 11 && var.iComensalsR > 0){
                                                                        System.out.println("Ha reservat taula per a "+var.iComensalsR);
                                                                        reserva[i].iComensalsR = var.iComensalsR;
                                                                        break;
                                                                    }else{ 
                                                                        System.out.println("\033[31m" + "*Comensals erronis*" + "\033[30m");
                                                                    }
                                                                }break;
                                                            }
                                                            case 3:{ //MODIFICAR DIA
                                                                while(true){
                                                                    System.out.println("Dia seleccionat actualment: "+reserva[i].iDiaReserva);
                                                                    var.iDiaReserva = sLlegirNumero(var.nDiaReserva);
                        
                                                                    if(var.iDiaReserva <= 29 && var.iDiaReserva > 0){
                                                                        System.out.println("Ha escollit el dia "+ var.iDiaReserva);
                                                                        reserva[i].iDiaReserva = var.iDiaReserva;
                                                                        break;
                                                                    }else{ 
                                                                        System.out.println("\033[31m" + "*Dia erroni*" + "\033[30m");
                                                                    }
                                                                }break;
                                                            }
                                                            case 4:{ //MODIFICAR MES
                                                                while(true){
                                                                    System.out.println("Mes seleccionat actualment: "+reserva[i].iMesReserva);
                                                                    var.iMesReserva = sLlegirNumero(var.nDiaReserva);
                                                    
                                                                    if(var.iMesReserva <= 12 && var.iMesReserva >= 0){
                                                                        System.out.println("Ha escollit "+sMeses[var.iMesReserva-1]);
                                                                        reserva[i].iMesReserva = var.iMesReserva;
                                                                        break;
                                                                    }else{
                                                                        System.out.println("\033[31m" + "*Mes erroni*" + "\033[30m");
                                                                    }
                                                                }break;
                                                            }
                                                            case 5:{ //TORNAR AL MENÚ
                                                                bModificarRes = false;
                                                                break;
                                                            }
                                                            default :{ //OPCIÓ INVALIDA
                                                                sOpcioInvalida();
                                                            }
                                                        }
                           
                                                    }else{ 
                                                        System.out.println("\033[31m" + "Reserva no trobada" + "\033[30m");
                                                    }
                                                }
                                            }break;
                                        }    
                                        case 5:{ //TANCA SESSIÓ
                                            System.out.println("\033[35m" + "Adeu fins la proxima!" + "\033[30m");
                                            break;
                                        }
                                        
                                        default:{ //OPCIÓ INVALIDA
                                            sOpcioInvalida();
                                        }
                                    }
                                }while(var.iOpcioSolicitud != 5);
                            //Comprova si es un usuari administrador    
                            }else if(usu.tipus.equals("admin")){
                                bAdmin = true;
                                while(bAdmin == true){
                                    
                                    Menus(Menus.MenuGestioReserves);
                                    var.iGestio = iLlegirOpcioMenu();
                                    
                                    //*************************************************************RESERVAS PER MES*//
                                    switch (var.iGestio){
                                        case 1:{ //RESERVA PER MES
                                            while(true){  
                                                //Mostra la reserva per mes
                                                Menus(Menus.MenuReservaPerMes);
                                                var.iMes = sLlegirNumero(var.nMesReserva);
                                                
                                                if(var.iMes == 13){//Comprova si l'usuari vol cancel·lar la cerca
                                                    System.out.println("\033[33m" + "Busqueda reserva per mes cancel·lada" + "\033[30m");
                                                    break;
                                                }else if(var.iMes > 0 && var.iMes < 13){
                                                    System.out.println("-----------\n "+ sMeses[var.iMes-1]+ " \n------------\n");
                                                    
                                                    var.iNumeroReserva = 1;
                                                    
                                                    //Busca les reserves del mes
                                                    for(int i = 0;i<1000;i++){
                                                        if(reserva[i].iMesReserva == (var.iMes) && reserva[i].iTelefon > 0){
                                                            System.out.println(" > RESERVA Nº"+ var.iNumeroReserva +" \n---------------\n"
                                                                + "Nom: "+reserva[i].sNomReserva +"\n"
                                                                + "Nº Comensals: "+reserva[i].iComensalsR +"\n"
                                                                + "Dia: "+reserva[i].iDiaReserva +"\n"
                                                                + "Mes: "+sMeses[reserva[i].iMesReserva-1] +"\n"
                                                                + "Telefon y numero de registre: "+reserva[i].iTelefon +"\n");
                                                            var.iNumeroReserva++;
                                                        }
                                                    }break;
                                                }else{
                                                    sOpcioInvalida();
                                                }
                                            }break;
                                        }
                                        //*************************************************************TOTES LES RESERVA*//
                                        case 2:{ 
                                            //Mostra totes les reserves
                                            System.out.println("---------------------\n TOTES LES RESERVES \n---------------------\n");
                                            var.iNumeroReserva = 1;
                                            
                                            for(int i = 0;i<1000;i++){
                                                if(reserva[i].iTelefon > 0){
                                                    System.out.println(" > RESERVA Nº"+ var.iNumeroReserva +" \n---------------\n"
                                                        + "Nom: "+reserva[i].sNomReserva+"\n"
                                                        + "Nº Comensals: "+reserva[i].iComensalsR+"\n"
                                                        + "Dia: "+reserva[i].iDiaReserva+"\n"
                                                        + "Mes: "+sMeses[reserva[i].iMesReserva-1]+"\n"
                                                        + "Telefon y numero de registre: "+reserva[i].iTelefon +"\n");
                                                    var.iNumeroReserva++;
                                                }
                                            }break;
                                        }
                                        
                                        case 3:{ //TANCAR SESSIÓ
                                            System.out.println("\033[35m" + "Adeu fins un altre!" + "\033[30m");
                                            bAdmin = false;
                                            break;
                                        }
                                        
                                        default:{ //OPCIÓ INVALIDA
                                            sOpcioInvalida();
                                        }
                                    }
                                }
                            }
                        }
                    }break;
                }
                //*************************************************************REGISTRE USUARI*//
                case 2:{ //REGISTAR USUARI
                     var.bRegistre = true;                  
                    
                    int iNRegistres = 0;//Posicio el primer espai buit a la base de dades de l'usuari
                    
                    while(var.bRegistre == true){
                        Menus(Menus.MenuRegistrarUsuari);
                        
                        var.bRegistreC = true;//Registre sempre es correcte
                        
                        var.sUsuari = sLlegirText(var.fUsuari);
                        
                        //Comprova si l'usuari vol cancel·lar el registre
                        if(var.sUsuari.equals("adeu")){
                            System.out.println("\033[33m" + "Registre nou cancel·lat" + "\033[30m");
                            break;
                        }
                        
                        var.sContrasenya = sLlegirText(var.fContrasenya);
                        var.sContrasenyaRep = sLlegirText(var.fRepContrasenya);
                        var.sCorreu = sLlegirText(var.fCorreu);
                        
                        //Comprova si l'usuari ja existeix
                        RG_ComprovaUsuari(var, sTUsuari);
                        
                        //Comprova si el correu ja s'esta utlitzant
                        RG_ComprovaCorreu(var, sTUsuari);
                        
                        //Comprova si les contrasenyes coincideixen
                        RG_ComprovaContrasenya(var);
                        
                        //Comprova que l'usuari es correcte
                        RG_GuardaUsuari(sTUsuari, var, db);
                    
                    }break;
                }
                //*************************************************************RECUPERAR CONTRASENYA*//
                case 3:{
                    RecuperarContrasenya(var, Menus, sTUsuari);
                    
                    break;
                }
                //Opcio del menú incorrecte
                default:{
                    sOpcioInvalida();
                }
            }
        }
    }
    /**
     * Mostra els menus enmagatzemats al class Menus.java
     * @param Menu 
     */
    private static void Menus(String[] Menu){
        for(int i = 0;i<Menu.length;i++){
            System.out.print(Menu[i]);
        }
    }
    
    private static void MostraErr(String Error){
        System.out.println(Error);
    }
    
    /**
     * Llegeix entrades dels Menus en nombres Int
     * @return 
     */
    private static int iLlegirOpcioMenu(){
        /**/
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Introdueixi una opció:\n -> ");
        int iOpcioMenu = sc.nextInt();
        
        return iOpcioMenu; 
    }
    
    /**
     * Llegeix entrades dels Menus en cadenes de caracters Strings i mostra el parametre Text
     * @param Text
     * @return 
     */
    private static String sLlegirText(String Text){
        Scanner sc = new Scanner(System.in);
        
        String sLectura;
        
        System.out.print(Text);
        sLectura = sc.nextLine();
        
        return sLectura;
    }
    
    /**
     * Llegeix entrades dels Menus en nombres Int i mostra el parametre Text
     * @param Text
     * @return 
     */
    private static int sLlegirNumero(String Text){
        Scanner sc = new Scanner(System.in);
        
        int iNumero;
        
        System.out.print(Text);
        iNumero = sc.nextInt();
      
        return iNumero;
    }
    
    /**
     * Retorna missatge de opció invalida
     */
    private static void sOpcioInvalida(){
        System.out.println("\033[31m" + "Has escollit una opció invalida" + "\033[30m");
    }
    
    /**
     * Comproba si el dia introduit esta entre els valors valids (1-29)
     * @param iDiaReserva
     * @param nDiaReserva
     * @return 
     */
    private static void ComprovarDia(Variables var){
        
        while(true){
            var.iDiaReserva = sLlegirNumero(var.nDiaReserva);
                if(var.iDiaReserva <= 29 && var.iDiaReserva > 0){
                    System.out.println("Ha escollit el dia "+var.iDiaReserva);
                    break;
                }else{
                    System.out.println("\033[31m" + "*Dia erroni*" + "\033[30m");
                }
        }
   
    }
    
    /**
     * Comproba si els comensals introduits estan entre els valors valids (1-10)
     * @param iComensalsR
     * @return 
     */
    private static void ComprovarComensals(Variables var){
            if(var.iComensalsR <= 11 && var.iComensalsR > 0){
                System.out.println("Ha reservat taula per a "+ var.iComensalsR);
                var.bReserva = false;
            }else{ 
                System.out.println("\033[31m" + "*Comensals erronis*" + "\033[30m");
                var.bReserva = true;
            }
        }
    
    /**
     * Comproba si el telefon introduit esta entre els valors valids (600.000.000 - 999.999.999)
     * @param nTelReserva
     * @param bTelefon
     * @param reserva
     * @return 
     */
    private static void ComprovarTelefon(Variables var,TReserva[] reserva){
        while(true){
            var.bTelefon = true;
            var.iTelefon = sLlegirNumero(var.nTelReserva);

            //Comprova que el telefon estigui dintre del rang valid
            if(var.iTelefon < 1000000000 && var.iTelefon > 600000000){
                for(int i = 0;i < 1000;i++){
                    if(var.iTelefon == reserva[i].iTelefon){
                        var.bTelefon = false;
                    }
                }
                if(var.bTelefon == true){
                    System.out.println("El telefon de contacte i el numero amb el qual podra modificar la reserva és "+ var.iTelefon);
                    break;
                }else{
                }        System.out.println("\033[31m" + "*Numero ocupat*" + "\033[01m");
            }else{
                System.out.println("\033[31m" + "*Format de telefon erroni*" + "\033[30m");
            }
        }
    }
    
    /**
     * Comproba si el mes introduit esta entre els valors valids (1-12)
     * @param iMesReserva
     * @param reserva
     * @param nMesReserva
     * @param sMeses
     * @return 
     */
    private static void ComprovarMes(Variables var,TReserva[] reserva,String[] sMeses){
        //Mes de la reserva
        var.bReserva = true;
        
        while(true){
            var.iMesReserva = sLlegirNumero(var.nMesReserva);
            
            if(var.iMesReserva == 13){//Comprova si es 13 per cancel·lar
                System.out.println("\033[33m" + "Sol·licitud de reserva cancel·lada" + "\033[30m");
                var.bReserva = false;
            }else if(var.iMesReserva <= 12 && var.iMesReserva >= 0){
                System.out.println("Ha escollit "+sMeses[var.iMesReserva-1]);
                reserva[var.c].iMesReserva = var.iMesReserva;
                break;
            }else{//Mes introduit erroni
                sOpcioInvalida();
            }
            
            if(var.bReserva == false)
                {break;}  
        }
    }
    
    /**
    * Mostra la reserva i retorna un boolea per comprobar si la cerca ha estat correcta o no
    * @param reserva
    * @param sMeses
    * @param var
    * @return 
    */
    private static void MostrarReserva(TReserva[] reserva,String[] sMeses,Variables var){
        for(int i = 0; i < 1000; i++){
            if(var.iTelefon == reserva[i].iTelefon){
                System.out.println("---------------------\n DADES DE LA RESERVA \n---------------------\n"
                    + "Nom: "+reserva[i].sNomReserva+"\n"
                    + "Nº Comensals: "+reserva[i].iComensalsR+"\n"
                    + "Dia: "+reserva[i].iDiaReserva+"\n"
                    + "Mes: "+sMeses[reserva[i].iMesReserva-1]+"\n"
                    + "Telefon y numero de registre: "+reserva[i].iTelefon);
                var.bBuscar = false;
                break;
            }
        }
    }
    
    /**
     * Case 3, Busca la reserva del client a traves del seu numero de telefon
     * @param reserva
     * @param sMeses
     * @param var
     * @param err
     * @param Menus 
     */
    private static void BuscarReserva (TReserva[] reserva,String[] sMeses,Variables var,Errores err,Menus Menus){
        var.bBuscar = true;  
        
        //Mostra el menu de Buscar Reserva
        Menus(Menus.MenuBuscarReserva);
        
        //Llegeix les dades per buscar reserva
        var.iTelefon = sLlegirNumero(var.nTelefonReserva);

        //Mostra la Reserva
        MostrarReserva(reserva, sMeses, var);
        //Comprueba si la reserva se ha mostrado, sino muestra mensaje de no encontrada
        if(var.bBuscar == true){
            MostraErr(err.sReservaNoTrobada);
        }
    }
    
    private static void EsborrarReserva (TReserva[] reserva,Variables var){
        for(int i = 0; i < var.c; i++){
            if(var.iTelefon==reserva[i].iTelefon){
                reserva[i].iTelefon=0;
                System.out.println("\033[32m" + "Reserva cancel·lada correctament" + "\033[30m");
                reserva[var.c].iComensalsR = 00;
                reserva[var.c].iDiaReserva = 30;
                reserva[var.c].iMesReserva = -1;
                reserva[var.c].iTelefon = 0;
                break;
            }else{
                System.out.println("\033[31m" + "Reserva no trobada" + "\033[30m");
            }
    }
}
    
    private static void CancelarReserva (TReserva[] reserva,String[] sMeses,Variables var,Errores err,Menus Menus){
       //Mostra el menu de Cancelar Reserva
        Menus(Menus.MenuCancelarReserva);
        //Llegeix les dades per cancelar reserva
        var.iTelefon = sLlegirNumero(var.nTelefonReserva);

        EsborrarReserva(reserva, var);
    }
    
    private static void ConnectarDB(BaseDades db){
        db.con = null;
        db.stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            db.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/restaurant",
                    "postgres", "root");
            db.con.setAutoCommit(false);
            System.out.println("Opened database successfully");

            db.stmt = db.con.createStatement();  
            
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    private static void QueryDB(String sQuery,BaseDades db){
        try {
            db.rs = db.stmt.executeQuery(sQuery);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    private static void UpdateDB(String sQuery,BaseDades db){
        try {
            db.stmt.executeUpdate(sQuery);
            db.con.setAutoCommit(true);
            db.con.commit();
        } catch (Exception e) {
        }
    }
    
    private static void DesconnectarDB(BaseDades db){
        try{
            db.stmt.close();
            db.con.close();
        } catch (Exception e){
        }
    }
    
    /**
     * Sol·licitar Reserva
     * @param Menus
     * @param reserva
     * @param sMeses
     * @param var 
     */
    private static void IntroduccionComprobacionDatos(Menus Menus,TReserva[] reserva,String[] sMeses,Variables var){
        var.bReserva = true;
        
        while(var.bReserva == true){
            //*Mostra el menu de solicitar reserva*//
            Menus(Menus.MenuSolicitarReserva);

            //Retorna la variable bReserva a true
            var.bReserva = true;
            reserva[0].idReserva = var.c;

            //Mes de la reserva
            ComprovarMes(var, reserva, sMeses);
            var.iMesReserva = reserva[var.c].iMesReserva;

            //Comprova si el mes es valid
            if(var.bReserva == false){break;}

            //Dia de la reserva
            ComprovarDia(var);

            //Numero de comensals
            while(var.bReserva == true){
                var.iComensalsR = sLlegirNumero(var.nComensalsReserva);
                ComprovarComensals(var);
            }

            //Nom de la reserva
            var.sNomReserva = sLlegirText(var.fNomReserva);

            //Numero de telefon
            ComprovarTelefon(var,reserva);
        }
        
    } 
    
    /**
     * Sol·licitr Reseva
     * @param var
     * @param db
     * @param sMeses 
     */
    private static void MostarDesarDades(Variables var,BaseDades db, String[] sMeses){
        Scanner sc = new Scanner(System.in);
        
        /**Mostrar Dades**/
        System.out.println("---------------\nRESUM DE LA RESERVA \n---------------\nNom: "+ var.sNomReserva +"\n"
            + "Nº Comensals: "+var.iComensalsR+"\n"
            + "Dia: "+var.iDiaReserva+"\n"
            + "Mes: "+sMeses[var.iMesReserva-1]+"\n"
            + "Telefon y numero de registre: "+var.iTelefon);

        System.out.print("Confirmar (s/n): ");
        var.sConfirmar = sc.next();
        
        /**Guardar Dades**/
        if(var.sConfirmar.equals("s")){
            ConnectarDB(db);
            String sQuery = ("INSERT INTO reserves (dia,mes,comensals,telefon,nom) "
                + "VALUES ("+ var.iDiaReserva +','+ var.iMesReserva 
                +','+ var.iComensalsR +','+ var.iTelefon +','+ '\''+var.sNomReserva+ '\'' +")");
            UpdateDB(sQuery, db);
            System.out.println("\033[32mReserva enregistrada correctament\033[30m");
            DesconnectarDB(db);
            var.c++;
        }else{
            System.out.println("\033[33mReserva cancel·lada\033[30m");
        }
    }
    
    /**
     * Inici Sessio
     * @param var
     * @param db
     * @param usu 
     */
    private static void ComprovarUsuari(Variables var,BaseDades db,Usuari usu){
        try {
            var.bUsuariC = false; //L'usuari sempre serà incorrecte
            String sQuery = "SELECT * FROM usuaris;";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                usu.usu = db.rs.getString("nom");
                usu.pass = db.rs.getString("contrasenya");
                usu.tipus = db.rs.getString("tipus");
                if(var.sUsuari.equals(usu.usu) && var.sContrasenya.equals(usu.pass)){
                    var.bUsuariC = true;
                    break;
                }
            }
        }  catch (Exception e) {
        }
        DesconnectarDB(db);
    }
    
    private static void SolicitarReserva(Variables var,BaseDades db,Menus Menus,String[] sMeses,TReserva[] reserva){
        while(true){
            //Introduccio i comprovacio de Dades    
            IntroduccionComprobacionDatos(Menus, reserva, sMeses, var);

            //Resum i guardar les dades
            MostarDesarDades(var, db, sMeses);

            break;
        }
    }
    
    /**
     * Recuperar contrasenya
     * @param var
     * @param sTUsuari 
     */
    private static void ComprovarCorreuUsuari(Variables var,String[][] sTUsuari){
        for(int i = 0;i<sTUsuari.length;i++){
            if(var.sUsuari.equals(sTUsuari[i][0])){
                if(var.sCorreu.equals(sTUsuari[i][2])){
                    var.bComprovacio = true;//Usuari i correu coincideixen

                    var.sContrasenya = sLlegirText(var.fNovaContrasenya);
                    var.sContrasenyaRep = sLlegirText(var.fRepContrasenya);

                    //Comprova les contrasenyes coincideixen
                    if(var.sContrasenya.equals(var.sContrasenyaRep)){
                        sTUsuari[i][1] = var.sContrasenya;
                        System.out.println("\033[32m" + "Contrasenya nova aplicada correctament" + "\033[30m");
                        var.bRecuperacio = false;
                    }else{
                         System.out.println("\033[31m" + "Les contrasenyes no coincideixen" + "\033[30m");
                    }
                }
            }
        }
    }
    
    /**
     * 
     * @param var
     * @param Menus
     * @param sTUsuari 
     */
    private static void RecuperarContrasenya(Variables var,Menus Menus,String[][] sTUsuari){
        var.bRecuperacio = true;

        while(var.bRecuperacio == true){
            Menus(Menus.MenuRecuperarContrasenya);

            //Comprovar si usuari i correu son corretes
            boolean bComprovacio = false;//Sempre es fals

            var.sUsuari = sLlegirText(var.fUsuari);

            //Comprova si l'usuari vol cancel·lar la recuperacio de contrasenya
            if(var.sUsuari.equals("adeu")){
                System.out.println("\033[33m" + "Recuperacio de contrasenya cancel·lada" + "\033[30m");
                break;
            }

            var.sCorreu = sLlegirText(var.fCorreu);

            //Comprovar si el correu i l'usuari son correctes
            ComprovarCorreuUsuari(var, sTUsuari);

            //Si l'usuari i correu no conicideixen
            if(bComprovacio == false){
                System.out.println("\033[31m" + "Usuari o correu incorrecte" + "\033[30m");
            }
        }
    }
    
    private static void RG_ComprovaUsuari(Variables var,String[][] sTUsuari){
        for(int i = 0;i<sTUsuari.length;i++){
            if(var.sUsuari.equals(sTUsuari[i][0])){
                System.out.println("\033[31m" + "Aquest usuari ja existeix" + "\033[30m");
                var.bRegistreC = false;//Registre incorrecte
            }
        }
    }
    
    private static void RG_ComprovaCorreu(Variables var,String[][] sTUsuari){
        for(int i = 0;i<sTUsuari.length;i++){
            if(var.sCorreu.equals(sTUsuari[i][2])){
                System.out.println("\033[31m" + "Aquest correu ja s'esta utilitzant" + "\033[30m");
                var.bRegistreC = false;//Registre incorrecte
            }
        }
    }
    
    private static void RG_ComprovaContrasenya(Variables var){
        if(var.sContrasenya.equals(var.sContrasenyaRep)){
        }else{
            System.out.println("\033[31m" + "Les contrasenyes no coincideixen" + "\033[30m");
            var.bRegistreC = false;//Registre incorrecte
        }
    }
    
    private static void RG_GuardaUsuari(String[][] sTUsuari,Variables var,BaseDades db){
        ConnectarDB(db);
        String sQuery = ("INSERT INTO usuaris (nom,contrasenya,correu,tipus) "
            + "VALUES ("+var.sUsuari + var.sContrasenya + var.sCorreu + "'usu'" +")");
        UpdateDB(sQuery, db);
        System.out.println("\033[32mReserva enregistrada correctament\033[30m");
        DesconnectarDB(db);
        /*if(var.bRegistreC == true){
            //Guarda les dades de l'usuari
            while(true){
                if(sTUsuari[iNRegistres][0] == null){//Busca una posicio buida
                    sTUsuari[iNRegistres][0] = var.sUsuari;
                    sTUsuari[iNRegistres][1] = var.sContrasenya;
                    sTUsuari[iNRegistres][2] = var.sCorreu;
                    sTUsuari[iNRegistres][3] = "usu";
                    System.out.println("\033[32m" + "S'ha creat l'usuari correctament" + "\033[30m");
                    break;
                }
                iNRegistres++;
            }
           */ var.bRegistre = false;//Tancamnet del menú de registres
        //}
    }
    
    
}