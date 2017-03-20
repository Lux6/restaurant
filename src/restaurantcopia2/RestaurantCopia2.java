/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantcopia2;

import java.util.Scanner;
import java.sql.*;

public class RestaurantCopia2 {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
      
        boolean bOP = true;//Bucle Menú Principal
        boolean bIniciar = true;//Bucle Iniciar Sessio
        boolean bRegistre = true;//Bucle Registrar usuari
        boolean bRecuperacio;//Bucle Recuperacio Contrasenya
        boolean bAdmin = true;//Bucle Menú administrador
        boolean bReserva = true;//Cancel·lar Sol·licitud Reserva
        boolean bModificarRes;//Bucle Menú Modificar Reserva
        boolean bTelefon = false;//Telefon unic
        
        String sUsuari;//Variable usuari
        String sContrasenya;//Variable contrasenya
        String sContrasenyaRep;//Variable contrasenya repetida
        String sCorreu;//Variable correu
        String[][] sTUsuari = new String[99][4];//Base de dades usuari
        String sConfirmar;//Variables confiramar reserva
        String sNomReserva;//Variable nom reserva
        
        String fNomReserva = "Introdueixi el nom al que vol fer la reserva:\n -> "; //Text demanar nom reserva
        String fUsuari = "Nom usuari:\n -> "; //Text demanar nom d'usuari
        String fContrasenya = "Contrasenya:\n -> "; //Text demanar contrasenya
        String fNovaContrasenya = "Nova Contrasenya:\n -> "; //Text demanr nova contrasenya (Registrar-se/Recuperar)
        String fRepContrasenya = "Repeteix Contrasenya:\n -> "; //Text demanar repetir contrsenya (Registrar usuari)
        String fNouUsuari = "Nou nom d'Usuari:\n -> "; //Text demanr nou nom d'usuari
        String fCorreu = "Introdueix el vostre correu\n -> "; //Text demanar correu (Registrar usuari)
        
        
        String nTelefonReserva = "Introdueixi el numero de telefon amb el que va fer la reserva:\n -> "; //Text demanar Telefon reserva(Visualitzat/Modificar)
        String nDiaReserva = "Introduexi ara el dia desitjat(1-29):\n -> "; //Text demanr dia reserva
        String nComensalsReserva = "Introduexi el numero de comensals(1-11):\n -> "; //Text demanr comensals reserva
        String nMesReserva = "Numero del mes de la reserva:\n -> "; //Text demanar Mes reserva
        String sMeses[] = {"Gener","Febrer","Març","Abril","Maig","Juny","Juliol","Agost","Septembre","Octubre","Novembre","Desembre"};
        

        //*Reservar espacio de clases*//   
        TReserva reserva[] = new TReserva[1000];
        Menus Menus = new Menus();
        Variables var = new Variables();
        Errores err = new Errores();
        
        //Reserva espai array class
        for(int i=0; i<1000; i++)reserva[i] = new TReserva();
        
        for(int i  = 0; i<100; i++){
            reserva[i].iTelefon = 0;
        }
        //********************************//Usuari creats inicialment
        //Usuari administrador
        sTUsuari[0][0] = "Paco";//Nom
        sTUsuari[0][1] = "edu32";//Contrasenya
        sTUsuari[0][2] = "paco@gmail.com";//Correu
        sTUsuari[0][3] = "admin";//Tipus d'usuari
        
        //Usuari comú
        sTUsuari[1][0] = "Manuel";//Nom
        sTUsuari[1][1] = "edu32";//Contrasenya
        sTUsuari[1][2] = "manuel@gmail.com";//Correu
        sTUsuari[1][3] = "usu";//Tipus d'usuari
        //********************************
        
        
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
                                        
                    boolean bUsuariC;//Comprovar si l'usuari es correcte
                    
                    while(bIniciar == true){
                        System.out.println("----------------\n INICIAR SESSIÓ \n----------------");
                        
                        System.out.println("Escriu 'adeu' per tornar al menu");
                        //Demanar i guardar nom d'usuari
                        sUsuari = sLlegirText(fUsuari);
                        //Comprovar si l'usuari vol cancel·lar l'inici de sessio
                        if(sUsuari.equals("adeu")){
                            System.out.println("\033[33m" + "Inici de sessió cancel·lat" + "\033[30m");
                            break;
                        }
                        //Demanar i guardar contrasenya
                        sContrasenya = sLlegirText(fContrasenya);
                        
                        //L'usuari sempre serà incorrecte
                        bUsuariC = false;
                        
                        //Comprovació de si l'usauri es correcte.
                        for(int i = 0;i<sTUsuari.length;i++){
                            if(sUsuari.equals(sTUsuari[i][0])){
                                if(sContrasenya.equals(sTUsuari[i][1])){
                                    //Contrasenya i usuari concideixen l'usuari es correcte
                                    bUsuariC = true;
                                    var.iUsuari = i;
                                }
                            }
                        }
                        
                        //Mostra missatge depenent si l'usuari s'ha connectat correctament o no
                        if(bUsuariC == false){
                            System.out.println("\033[47m" + "\033[31m" + "Usuari o contrasenya incorrecte" + "\033[30m");
                        }else{
                            System.out.println("\033[47m" + "\033[32m" + "S'ha iniciat la sessió correctament" + "\033[30m");
                            //Comprova si l'usuari es un usuari client
                            if(sTUsuari[var.iUsuari][3].equals("usu")){
                                do{    
                                    //Menu principal
                                    Menus(Menus.MenuReserves);
                                    var.iOpcioSolicitud = iLlegirOpcioMenu();
                    
                                    switch(var.iOpcioSolicitud){
                                        case 1:{ //SOLICITAR RESERVA
                                            bReserva = true;
            
                                                while(true){
                                                    
                                                    Menus(Menus.MenuSolicitarReserva);

                                                        bReserva = true;
                                                        reserva[0].idReserva = var.c;
                                                        
                                                    //Mes de la reserva
                                                    bReserva = comprobacionMes(var.iMesReserva, reserva, nMesReserva, sMeses);
                                                    var.iMesReserva = reserva[var.c].iMesReserva;
                                                    //Si ha introdueit un mes valid continua amb la reserva
                                                    if(bReserva == false){break;}
                                                    //Dia de la reserva
                                                    var.iDiaReserva = comprobacionDia(var.iDiaReserva,nDiaReserva);

                                                    //Numero de comensals
                                                    while(bReserva == true){
                                                        var.iComensalsR = sLlegirNumero(nComensalsReserva);
                                                        bReserva = comprobacionComensals(var.iComensalsR);
                                                    }
                                                
                                                    //Nom de la reserva
                                                    sNomReserva = sLlegirText(fNomReserva);
                    
                                                    //Numero de telefon
                                                    var.iTelefon = comprobacioTelefono(var.nTelReserva,bTelefon,reserva);
                                                    
                                                //Resum i guardar les dades
                                                System.out.println("---------------\nRESUM DE LA RESERVA \n---------------\nNom: "+sNomReserva+"\n"
                                                    + "Nº Comensals: "+var.iComensalsR+"\n"
                                                    + "Dia: "+var.iDiaReserva+"\n"
                                                    + "Mes: "+sMeses[var.iMesReserva-1]+"\n"
                                                    + "Telefon y numero de registre: "+var.iTelefon);
                    
                                                System.out.print("Confirmar (s/n): ");
                                                sConfirmar = sc.next();
                         
                                                if(sConfirmar.equals("s")){
                                                    reserva[var.c].sNomReserva = sNomReserva;
                                                    reserva[var.c].iDiaReserva = var.iDiaReserva;
                                                    reserva[var.c].iComensalsR = var.iComensalsR;
                                                    reserva[var.c].iTelefon = var.iTelefon;
                                                    System.out.println("\033[32mReserva enregistrada correctament\033[30m");
                                                    var.c++;
                                                }else{
                                                    System.out.println("\033[33mReserva cancel·lada\033[30m");
                                                }break;
                                            }break;
                                        } //*************************************************************CANCELAR RESERVA*//
                                        case 2:{ 
                                            CancelarReserva(reserva, sMeses, var, err, Menus);
                                            break;
                                        }
                                        //*************************************************************BUSCAR RESERVA*//
                                        case 3:{
                                            BuscarReserva(reserva, sMeses, var, err, Menus);
                                            break;
                                        }
                                        //*************************************************************MODIFICAR RESERVA*//
                                        case 4:{ 
                                            
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
                                                                
                                                                sNomReserva = sLlegirText(fNouUsuari);
                                                                
                                                                System.out.println("Nom modificat a " + sNomReserva);
                                                                reserva[i].sNomReserva = sNomReserva;
                                                                break;
                                                            }
                                                            case 2:{ //MODIFICAR COMENSALS
                                                                while(true){
                                                                    System.out.println("Nº actual de comensals: "+reserva[i].iComensalsR);
                                                                    var.iComensalsR = sLlegirNumero(nComensalsReserva);
                        
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
                                                                    var.iDiaReserva = sLlegirNumero(nDiaReserva);
                        
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
                                                                    var.iMesReserva = sLlegirNumero(nDiaReserva);
                                                    
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
                            }else if(sTUsuari[var.iUsuari][3].equals("admin")){
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
                                                var.iMes = sLlegirNumero(nMesReserva);
                                                
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
                                        
                    boolean bRegistreC;//Comprovar si el registre s'ha fet correctament
                    int iNRegistres = 0;//Posicio el primer espai buit a la base de dades de l'usuari
                    
                    while(bRegistre == true){
                        Menus(Menus.MenuRegistrarUsuari);
                        
                        bRegistreC = true;//Registre sempre es correcte
                        
                        sUsuari = sLlegirText(fUsuari);
                        
                        //Comprova si l'usuari vol cancel·lar el registre
                        if(sUsuari.equals("adeu")){
                            System.out.println("\033[33m" + "Registre nou cancel·lat" + "\033[30m");
                            break;
                        }
                        
                        sContrasenya = sLlegirText(fContrasenya);
                        sContrasenyaRep = sLlegirText(fRepContrasenya);
                        sCorreu = sLlegirText(fCorreu);
                        
                        //Comprova si l'usuari ja existeix
                        for(int i = 0;i<sTUsuari.length;i++){
                            if(sUsuari.equals(sTUsuari[i][0])){
                                System.out.println("\033[31m" + "Aquest usuari ja existeix" + "\033[30m");
                                bRegistreC = false;//Registre incorrecte
                            }
                            
                        }
                        //Comprova si el correu ja s'esta utlitzant
                        for(int i = 0;i<sTUsuari.length;i++){
                            if(sCorreu.equals(sTUsuari[i][2])){
                                System.out.println("\033[31m" + "Aquest correu ja s'esta utilitzant" + "\033[30m");
                                bRegistreC = false;//Registre incorrecte
                            }
                        }
                        //Comprova si les contrasenyes coincideixen
                        if(sContrasenya.equals(sContrasenyaRep)){
                            
                        }else{
                            System.out.println("\033[31m" + "Les contrasenyes no coincideixen" + "\033[30m");
                            bRegistreC = false;//Registre incorrecte
                        }
                        //Comprova que l'usuari es correcte
                        if(bRegistreC == true){
                            //Guarda les dades de l'usuari
                            while(true){
                                if(sTUsuari[iNRegistres][0] == null){//Busca una posicio buida
                                    sTUsuari[iNRegistres][0] = sUsuari;
                                    sTUsuari[iNRegistres][1] = sContrasenya;
                                    sTUsuari[iNRegistres][2] = sCorreu;
                                    sTUsuari[iNRegistres][3] = "usu";
                                    System.out.println("\033[32m" + "S'ha creat l'usuari correctament" + "\033[30m");
                                    break;
                                }
                                iNRegistres++;
                            }
                            bRegistre = false;//Tancamnet del menú de registres
                        }
                    }break;
                }
                //*************************************************************RECUPERAR CONTRASENYA*//
                case 3:{
                    
                    bRecuperacio = true;
                    
                    while(bRecuperacio == true){
                        Menus(Menus.MenuRecuperarContrasenya);
                        
                        //Comprovar si usuari i correu son corretes
                        boolean bComprovacio = false;//Sempre es fals
                    
                        sUsuari = sLlegirText(fUsuari);
                        
                        //Comprova si l'usuari vol cancel·lar la recuperacio de contrasenya
                        if(sUsuari.equals("adeu")){
                            System.out.println("\033[33m" + "Recuperacio de contrasenya cancel·lada" + "\033[30m");
                            break;
                        }
                        
                        sCorreu = sLlegirText(fCorreu);
                    
                        //Comprovar si el correu i l'usuari son correctes
                        for(int i = 0;i<sTUsuari.length;i++){
                            if(sUsuari.equals(sTUsuari[i][0])){
                                if(sCorreu.equals(sTUsuari[i][2])){
                                    bComprovacio = true;//Usuari i correu coincideixen
                                    
                                    sContrasenya = sLlegirText(fNovaContrasenya);
                                    sContrasenyaRep = sLlegirText(fRepContrasenya);
                                    
                                    //Comprova les contrasenyes coincideixen
                                    if(sContrasenya.equals(sContrasenyaRep)){
                                        sTUsuari[i][1] = sContrasenya;
                                        System.out.println("\033[32m" + "Contrasenya nova aplicada correctament" + "\033[30m");
                                        bRecuperacio = false;
                                    }else{
                                         System.out.println("\033[31m" + "Les contrasenyes no coincideixen" + "\033[30m");
                                    }
                                }
                            }
                        }
                        //Si l'usuari i correu no conicideixen
                        if(bComprovacio == false){
                            System.out.println("\033[31m" + "Usuari o correu incorrecte" + "\033[30m");
                        }
                    }break;
                }
                //Opcio del menú incorrecte
                default:{
                    sOpcioInvalida();
                }
            }
        }
    }
    /**
    *Mostra els menus enmagatzemats al class Menus.java
    */
    private static void Menus(String[] Menu){
        for(int i = 0;i<Menu.length;i++){
            System.out.print(Menu[i]);
        }
    }
    private static void MostraErr(String Error){
        System.out.print(Error);
    }
    /***
    *Llegeix entrades dels Menus en nombres Int
    */
    private static int iLlegirOpcioMenu(){
        /**/
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Introdueixi una opció:\n -> ");
        int iOpcioMenu = sc.nextInt();
        
        return iOpcioMenu; 
    }
    /**
    *Llegeix entrades dels Menus en cadenes de caracters Strings i mostra el parametre Text
    */
    private static String sLlegirText(String Text){
        Scanner sc = new Scanner(System.in);
        
        String sLectura;
        
        System.out.print(Text);
        sLectura = sc.nextLine();
        
        return sLectura;
    }
    /**
    *Llegeix entrades dels Menus en nombres Int i mostra el parametre Text
    */
    private static int sLlegirNumero(String Text){
        Scanner sc = new Scanner(System.in);
        
        int iNumero;
        
        System.out.print(Text);
        iNumero = sc.nextInt();
      
        return iNumero;
    }
    /**
    *Retorna missatge de opció invalida
    */
    private static void sOpcioInvalida(){
        System.out.println("\033[31m" + "Has escollit una opció invalida" + "\033[30m");
    }
    /**
    *Comproba si el dia introduit esta entre els valors valids (1-29)
    */
    private static int comprobacionDia(int iDiaReserva, String nDiaReserva){
        
        while(true){
            iDiaReserva = sLlegirNumero(nDiaReserva);
                if(iDiaReserva <= 29 && iDiaReserva > 0){
                    System.out.println("Ha escollit el dia "+iDiaReserva);
                    break;
                }else{
                    System.out.println("\033[31m" + "*Dia erroni*" + "\033[30m");
                }
        }
        return iDiaReserva;
   
    }
    /**
    *Comproba si els comensals introduits estan entre els valors valids (1-10)
    */
    private static boolean comprobacionComensals(int iComensalsR){
            if(iComensalsR <= 11 && iComensalsR > 0){
                System.out.println("Ha reservat taula per a "+ iComensalsR);
                return false;
            }else{ 
                System.out.println("\033[31m" + "*Comensals erronis*" + "\033[30m");
            }return true;
        }
    /**
    **Comproba si el telefon introduit esta entre els valors valids (600.000.000 - 999.999.999)
    */
    private static int comprobacioTelefono(String nTelReserva,boolean bTelefon,TReserva[] reserva){
        int iTelefon = 0;
        while(true){
            bTelefon = true;
            iTelefon = sLlegirNumero(nTelReserva);

            //Comprova que el telefon estigui dintre del rang valid
            if(iTelefon < 1000000000 && iTelefon > 600000000){
                for(int i = 0;i < 1000;i++){
                    if(iTelefon == reserva[i].iTelefon){
                        bTelefon = false;
                    }
                }
                if(bTelefon == true){
                    System.out.println("El telefon de contacte i el numero amb el qual podra modificar la reserva és "+ iTelefon);
                        break;
                    }else{
                        System.out.println("\033[31m" + "*Numero ocupat*" + "\033[01m");
                    }
                    }else{
                        System.out.println("\033[31m" + "*Format de telefon erroni*" + "\033[30m");
                    }
        }
        return iTelefon;
    }
    /**
    *Comproba si el mes introduit esta entre els valors valids (1-12)
    */
    private static boolean comprobacionMes(int iMesReserva,TReserva[] reserva,String nMesReserva,String[] sMeses){
        //Mes de la reserva
        int c = reserva[0].idReserva;
        boolean bReserva = true;
        while(true){
                iMesReserva = sLlegirNumero(nMesReserva);
                    if(iMesReserva == 13){//Comprova si es 13 per cancel·lar
                        System.out.println("\033[33m" + "Sol·licitud de reserva cancel·lada" + "\033[30m");
                        bReserva = false;
                        }else if(iMesReserva <= 12 && iMesReserva >= 0){
                            System.out.println("Ha escollit "+sMeses[iMesReserva-1]);
                            reserva[c].iMesReserva = iMesReserva;
                            break;
                        }else{//Mes introduit erroni
                            sOpcioInvalida();
                        }
            if(bReserva == false){break;}  
        }
        return bReserva;
    }
    /**
     * Mostra la reserva i retorna un boolea per comprobar si la cerca ha estat correcta o no
     */
    private static boolean MostrarReserva(TReserva[] reserva,String[] sMeses,Variables var){
       boolean bTrue = true;
       
        for(int i = 0; i < 1000; i++){
            if(var.iTelefon == reserva[i].iTelefon){
                System.out.println("---------------------\n DADES DE LA RESERVA \n---------------------\n"
                    + "Nom: "+reserva[i].sNomReserva+"\n"
                    + "Nº Comensals: "+reserva[i].iComensalsR+"\n"
                    + "Dia: "+reserva[i].iDiaReserva+"\n"
                    + "Mes: "+sMeses[reserva[i].iMesReserva-1]+"\n"
                    + "Telefon y numero de registre: "+reserva[i].iTelefon);
                bTrue = false;
                break;
            }
        }
        return bTrue;
    }
    /**
     * Case 3, Busca la reserva del client a traves del seu numero de telefon
     */
    private static void BuscarReserva (TReserva[] reserva,String[] sMeses,Variables var,Errores err,Menus Menus){
        boolean bComprobacion = true;  
        
        //Mostra el menu de Buscar Reserva
        Menus(Menus.MenuBuscarReserva);
        //Llegeix les dades per buscar reserva
        var.iTelefon = sLlegirNumero(var.nTelefonReserva);

        //Mostra la Reserva
        bComprobacion = MostrarReserva(reserva, sMeses, var);
        //Comprueba si la reserva se ha mostrado, sino muestra mensaje de no encontrada
        if(bComprobacion == true){
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
    
}