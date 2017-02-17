/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantcopia2;

import java.util.Scanner;

public class RestaurantCopia2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String[] MenuPrincipal = {"------------\n RESTAURANT \n------------\n ",
            "[1] Iniciar Sessio\n ",
            "[2] Registrar-se\n ",
            "[3] Recuperar Contrasenya\n ",
            "[4] Tancar aplicació"};
        
        int iOpcioPrincipal;//Opcio menú
        int iOpcioReserva;//Opcio reserva
        int iOpcioSolicitud;//Opcio Sol·licitud Reserva
        int iOpcioModificar;//Opcio Modificar Reserva
        int iUsuari = 0;//Posicio usuari admin/usu
        int iGestio = 0;//Opcions administrador
        int iMes = 0;//Mes per cercar reserves administrador
        int iNumeroReserva = 0;//Numero de reserva 
        int c = 0;//Posicio Base de dades reserva
        int iTelefon;//Variable numero telefon
        int iMesReserva;//Variable Mes reserva
        int iDiaReserva;//Variable dia reserva
        int iComensalsR;//Variable comensals reserva
        
        boolean bOP = true;//Bucle Menú Principal
        boolean bIniciar = true;//Bucle Iniciar Sessio
        boolean bRegistre = true;//Bucle Registrar usuari
        boolean bRecuperacio;//Bucle Recuperacio Contrasenya
        boolean bAdmin = true;//Bucle Menú administrador
        boolean bReserva = true;//Cancel·lar Sol·licitud Reserva
        boolean bCercaReserva = true;//Missateg Error Cerca Reserva
        boolean bModificarRes;//Bucle Menú Modificar Reserva
        boolean bTelefon = false;//Telefon unic
        
        String sUsuari;//Variable usuari
        String sContrasenya;//Variable contrasenya
        String sContrasenyaRep;//Variable contrasenya repetida
        String sCorreu;//Variable correu
        String[][] sTUsuari = new String[99][4];//Base de dades usuari
        String sConfirmar;//Variables confiramar reserva
        String sNomReserva;//Variable nom reserva

        //Base de dades reserva
        class TReserva{
            int idReserva;
            String sNomReserva;
            int iDiaReserva;
            int iMesReserva;
            int iComensalsR;
            long iTelefon;
        }
        String sMeses[ ] = {"Gener","Febrer","Març","Abril","Maig","Juny","Juliol","Agost","Septembre","Octubre","Novembre","Desembre"};
                
        TReserva reserva[] = new TReserva[1000];
        
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
        sTUsuari[1][2] = "paco@gmail.com";//Correu
        sTUsuari[1][3] = "usu";//Tipus d'usuari
        //********************************
        Menus(MenuPrincipal);
        
        while(bOP == true){
            /*System.out.println("------------\n RESTAURANT \n------------\n "
                + "[1] Iniciar Sessio\n "
                + "[2] Registrar-se\n "
                + "[3] Recuperar Contrasenya\n "
                + "[4] Tancar aplicació");*/
            System.out.print("Escull una opció: ");
            iOpcioPrincipal = sc.nextInt();
            //Comprovar si l'usuari vol tancar el programa.
            if(iOpcioPrincipal == 4){
                System.out.println("\033[34mAdeu!");
                break;
            }
            
            switch(iOpcioPrincipal){
                case 1:{ //INICI SESSIÓ
                                        
                    boolean bUsuariC;//Comprovar si l'usuari es correcte
                    
                    while(bIniciar == true){
                        System.out.println("----------------\n INICIAR SESSIÓ \n----------------");
                        
                        System.out.println("Escriu 'adeu' per tornar al menu");
                        //Demanar i guardar nom d'usuari
                        System.out.print("Nom usuari: ");
                        sUsuari = sc.next();
                        //Comprovar si l'usuari vol cancel·lar l'inici de sessio
                        if(sUsuari.equals("adeu")){
                            System.out.println("\033[33mInici de sessió cancel·lat\033[30m");
                            break;
                        }
                        //Demanar i guardar contrasenya
                        System.out.print("Contrasenya: ");
                        sContrasenya = sc.next();
                        
                        //L'usuari sempre serà incorrecte
                        bUsuariC = false;
                        
                        //Comprovació de si l'usauri es correcte.
                        for(int i = 0;i<sTUsuari.length;i++){
                            if(sUsuari.equals(sTUsuari[i][0])){
                                if(sContrasenya.equals(sTUsuari[i][1])){
                                    //Contrasenya i usuari concideixen l'usuari es correcte
                                    bUsuariC = true;
                                    iUsuari = i;
                                }
                            }
                        }
                        
                        //Mostra missatge depenent si l'usuari s'ha connectat correctament o no
                        if(bUsuariC == false){
                            System.out.println("\033[47m\033[31mUsuari o contrasenya incorrecte\033[30m");
                        }else{
                            System.out.println("\033[47m\033[32mS'ha iniciat la sessió correctament\033[30m");
                            //Comprova si l'usuari es un usuari client
                            if(sTUsuari[iUsuari][3].equals("usu")){
                                do{    
                                    //Menu principal
                                    System.out.println("---------------\n MENU RESERVES\n---------------\n "
                                        + "[1] Sol·licitar reserva\n "
                                        + "[2] Cancel·lar reserva\n "
                                        + "[3] Buscar reserva\n "
                                        + "[4] Modificar reserva\n "
                                        + "[5] Tancar sessió ");
                                    System.out.print("Escull una opció: ");
                                    iOpcioSolicitud = sc.nextInt();
                    
                                    switch(iOpcioSolicitud){
                                        case 1:{ //SOLICITAR RESERVA
                                            bReserva = true;
                                            
                                            while(true){
                                            
                                                while(true){
                                                    //Mes de la reserva
                                                    System.out.println("---------------\n SOL·LICITAR RESERVA \n---------------\n"
                                                        + "Introeudix '13' per tornar al menú");
                                                    System.out.print("Numero del mes de la reserva: ");
                                                    iMesReserva = sc.nextInt();
                                                    
                                                    if(iMesReserva == 13){//Comprova si es 13 per cancel·lar
                                                        System.out.println("\033[33mSol·licitud de reserva cancel·lada\033[30m");
                                                        bReserva = false;
                                                    }else if(iMesReserva <= 12 && iMesReserva >= 0){
                                                        System.out.println("Ha escollit "+sMeses[iMesReserva-1]);
                                                        break;
                                                    }else{
                                                        System.out.println("\033[31m*Mes erroni*\033[30m");
                                                    }
                                                    
                                                    //Si ha introdueit un mes valid continua amb la reserva
                                                    if(bReserva == false){break;}
                                                }
                                                
                                                //Si ha cancel·lat la reserva torna al menú
                                                if(bReserva == false){break;}
                            
                                                //Dia de la reserva
                                                while(true){
                                                    System.out.print("Introduexi ara el dia desitjat(1-29): ");
                                                    iDiaReserva = sc.nextInt();
                        
                                                    if(iDiaReserva <= 29 && iDiaReserva > 0){
                                                        System.out.println("Ha escollit el dia "+iDiaReserva);
                                                        break;
                                                    }else{ 
                                                        System.out.println("\033[31m*Dia erroni*\033[30m");
                                                    }
                      
                                                }
                                                
                            
                                                //Numero de comensals
                                                while(true){
                                                    System.out.print("Introduexi el numero de comensals(1-11): ");
                                                    iComensalsR = sc.nextInt();
                        
                                                    if(iComensalsR <= 11 && iComensalsR > 0){
                                                        System.out.println("Ha reservat taula per a "+ iComensalsR);
                                                        break;
                                                    }else{ 
                                                        System.out.println("\033[31m*Comensals erronis*\033[30m");
                                                    }
                      
                                                }
                                                
                                                //Nom de la reserva
                                                System.out.print("Introdueixi el nom al que vol fer la reserva: ");
                                                sNomReserva = sc.nextLine();
                                                sNomReserva = sc.nextLine();
                    
                                                //Numero de telefon
                                                while(true){
                                                    bTelefon = true;
                                                    
                                                    System.out.print("Introduexi el numero de telefon: ");
                                                    iTelefon = sc.nextInt();
                        
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
                                                            System.out.println("\033[31m*Numero ocupat*\033[01m");
                                                        }
                                                    
                                                    }else{
                                                        System.out.println("\033[31m*Format de telefon erroni*\033[30m");
                                                    }
                      
                                                }
                    
                                                //Resum i guardar les dades
                                                System.out.println("---------------\nRESUM DE LA RESERVA \n---------------\nNom: "+sNomReserva+"\n"
                                                    + "Nº Comensals: "+iComensalsR+"\n"
                                                    + "Dia: "+iDiaReserva+"\n"
                                                    + "Mes: "+sMeses[iMesReserva-1]+"\n"
                                                    + "Telefon y numero de registre: "+iTelefon);
                    
                                                System.out.print("Confirmar (s/n): ");
                                                sConfirmar = sc.next();
                         
                                                if(sConfirmar.equals("s")){
                                                    reserva[c].sNomReserva = sNomReserva;
                                                    reserva[c].iMesReserva = iMesReserva;
                                                    reserva[c].iDiaReserva = iDiaReserva;
                                                    reserva[c].iComensalsR = iComensalsR;
                                                    reserva[c].iTelefon = iTelefon;
                                                    System.out.println("\033[32mReserva enregistrada correctament\033[30m");
                                                    c++;
                                                }else{
                                                    System.out.println("\033[33mReserva cancel·lada\033[30m");
                                                }break;
                                            }break;
                                        }
                                        
                                        case 2:{ //CANCELAR RESERVA
                                            System.out.print("---------------\n CANCEL·LACIÓ RESERVA \n---------------\n");
                                            System.out.println("Introdueixi el numero de telefon amb el que va fer la reserva");
                    
                                            iTelefon=sc.nextInt();
                    
                                            for(int i = 0; i < c; i++){
                                                if(iTelefon==reserva[i].iTelefon){
                                                    reserva[i].iTelefon=0;
                                                    System.out.println("\033[32mReserva cancel·lada correctament\033[30m");
                                                    reserva[c].iComensalsR = 25;
                                                    reserva[c].iDiaReserva = 30;
                                                    reserva[c].iMesReserva = -1;
                                                    reserva[c].iTelefon = 0;
                                                    break;
                                                }else{
                                                    System.out.println("\033[31mReserva no trobada\033[30m");
                                                }
                                            }break;
                                        }
                                        
                                        case 3:{ //BUSCAR RESERVA
                                            System.out.println("-----------------\n BUSQUEDA RESERVA \n-----------------\n "
                                                + "Introeudix '13' per tornar al menú\n "
                                                + "Introdueixi el numero de telefon amb el que va fer la reserva:");
                                            iTelefon=sc.nextInt();
                                            
                                            if(iTelefon == 13){ //Comprova si vol cancel·lar la cerca
                                                System.out.println("\033[33mBusqueda de reserva cancel·lada\033[30m");
                                                break;
                                            }
                                            
                                            //Cerca la reserva
                                            bCercaReserva = true;
                                            for(int i = 0; i < 1000; i++){
                                                if(iTelefon == reserva[i].iTelefon){
                                                    System.out.println("---------------------\n DADES DE LA RESERVA \n---------------------\n"
                                                        + "Nom: "+reserva[i].sNomReserva+"\n"
                                                        + "Nº Comensals: "+reserva[i].iComensalsR+"\n"
                                                        + "Dia: "+reserva[i].iDiaReserva+"\n"
                                                        + "Mes: "+sMeses[reserva[i].iMesReserva-1]+"\n"
                                                        + "Telefon y numero de registre: "+reserva[i].iTelefon);
                                                    bCercaReserva = false;
                                                    break;
                                                }
                                            }
                                            if(bCercaReserva == true){
                                                System.out.println("\033[31mReserva no trobada.\033[30m");
                                            }break;
                                        }
                                        case 4:{ //MODIFICAR RESERVA
                                            
                                            bModificarRes = true;
                                            
                                            while(bModificarRes == true){
                                                System.out.print("---------------\n MODIFICAR RESERVA \n---------------\n ");
                                                System.out.println("Introdueix 13 per cancel·lar la modificacio");
                                                System.out.println("Introdueixi el numero de telefon amb el que va fer la reserva");
                                                    
                                                iTelefon=sc.nextInt();

                                                if(iTelefon == 13){
                                                    System.out.println("\033[33mModificacio de resrva cancel·lada\033[30m");
                                                    break;
                                                }
                                                
                                                for(int i = 0; i < c; i++){
                                                    if(iTelefon==reserva[i].iTelefon){
                                                        System.out.println("---------------\nDADES DE LA RESERVA\n---------------\n"
                                                            + "Nom: "+reserva[i].sNomReserva+"\n"
                                                            + "Nº Comensals: "+reserva[i].iComensalsR+"\n"
                                                            + "Dia: "+reserva[i].iDiaReserva+"\n"
                                                            + "Mes: "+sMeses[reserva[i].iMesReserva-1]+"\n"
                                                            + "Telefon y numero de registre: "+reserva[i].iTelefon);
                            
                                                        System.out.println("Indique que voleu modificar:\n "
                                                            + "[1] Nom\n "
                                                            + "[2] Nº de comensals\n "
                                                            + "[3] Dia\n "
                                                            + "[4] Mes\n "
                                                            + "[5] Tornar al menú");
                                                            System.out.print("Escull una opció: ");
                                                            iOpcioModificar = sc.nextInt();
                            
                                                        switch(iOpcioModificar){
                                                            case 1:{ //MODIFICAR NOM
                                                                System.out.println("Nom actual "+ reserva[i].sNomReserva);
                                                                System.out.print("Nou nom:: ");
                                                                sNomReserva = sc.nextLine();
                                                                sNomReserva = sc.nextLine();
                                                                System.out.println("Nom modificat a " + sNomReserva);
                                                                reserva[i].sNomReserva = sNomReserva;
                                                                break;
                                                            }
                                                            case 2:{ //MODIFICAR COMENSALS
                                                                while(true){
                                                                    System.out.println("Nº actual de comensals: "+reserva[i].iComensalsR);
                                                                    System.out.print("Nou numero de comensals(1-11): ");
                                                                    iComensalsR = sc.nextInt();
                        
                                                                    if(iComensalsR <= 11 && iComensalsR > 0){
                                                                        System.out.println("Ha reservat taula per a "+iComensalsR);
                                                                        reserva[i].iComensalsR = iComensalsR;
                                                                        break;
                                                                    }else{ 
                                                                        System.out.println("\033[31m*Comensals erronis*\033[30m");
                                                                    }
                      
                                                                }break;
                                                            }
                                                            case 3:{ //MODIFICAR DIA
                                                                while(true){
                                                                    System.out.println("Dia seleccionat actualment: "+reserva[i].iDiaReserva);
                                                                    System.out.print("Introduexi ara el dia desitjat(1-29): ");
                                                                    iDiaReserva = sc.nextInt();
                        
                                                                    if(iDiaReserva <= 29 && iDiaReserva > 0){
                                                                        System.out.println("Ha escollit el dia "+ iDiaReserva);
                                                                        reserva[i].iDiaReserva = iDiaReserva;
                                                                        break;
                                                                    }else{ 
                                                                        System.out.println("\033[31m*Dia erroni*\033[30m");
                                                                    }
                                                                }break;
                                                            }
                                                            case 4:{ //MODIFICAR MES
                                                                while(true){
                                                                    System.out.println("Mes seleccionat actualment: "+reserva[i].iMesReserva);
                                                                    System.out.print("Numero del mes de la reserva: ");
                                                                    iMesReserva = sc.nextInt();
                                                    
                                                                    if(iMesReserva <= 12 && iMesReserva >= 0){
                                                                        System.out.println("Ha escollit "+sMeses[iMesReserva-1]);
                                                                        reserva[i].iMesReserva = iMesReserva;
                                                                        break;
                                                                    }else{
                                                                        System.out.println("\033[31m*Mes erroni*\033[30m");
                                                                    }
                                                                }break;
                                                            }
                                                            case 5:{ //TORNAR AL MENÚ
                                                                bModificarRes = false;
                                                                break;
                                                            }
                                                            default :{ //OPCIÓ INVALIDA
                                                                System.out.println("\033[31m*Opció invalida\033[30m");
                                                            }
                                                        }
                           
                                                    }else{ 
                                                        System.out.println("\033[31mReserva no trobada\033[30m");
                                                    }
                                                }
                                            }break;
                                        }    
                                        case 5:{ //TANCA SESSIÓ
                                            System.out.println("\033[35mAdeu fins la proxima!\033[30m");
                                            break;
                                        }
                                        
                                        default:{ //OPCIÓ INVALIDA
                                            System.out.println("\033[31mHas escollit una opció invalida\033[30m");
                                        }
                                    }
                                }while(iOpcioSolicitud != 5);
                            //Comprova si es un usuari administrador    
                            }else if(sTUsuari[iUsuari][3].equals("admin")){
                                bAdmin = true;
                                while(bAdmin == true){
                                    System.out.println("--------------------\n GESTIO DE RESERVES \n--------------------\n "
                                        +"[1] Reserves per mes\n "
                                        +"[2] Totes les reserves\n "
                                        +"[3] Tancar Sessio");
                                    System.out.print("Escull una opcio: ");
                                    iGestio = sc.nextInt();
                                    
                                    
                                    switch (iGestio){
                                        case 1:{ //RESERVA PER MES
                                            while(true){  
                                                //Mostra la reserva per mes
                                                System.out.println("\n-------------------\n RESERVES PER MES \n-------------------\n "
                                                    + "Introeudix '13' per tornar al menú");
                                                System.out.print("Numero del mes que vols visualitzar: ");
                                                iMes = sc.nextInt();
                                                
                                                if(iMes == 13){//Comprova si l'usuari vol cancel·lar la cerca
                                                    System.out.println("\033[33mBusqueda reserva per mes cancel·lada\033[30m");
                                                    break;
                                                }else if(iMes > 0 && iMes < 13){
                                                    System.out.println("-----------\n "+ sMeses[iMes-1]+ " \n------------\n");
                                                    
                                                    iNumeroReserva = 1;
                                                    
                                                    //Busca les reserves del mes
                                                    for(int i = 0;i<1000;i++){
                                                        if(reserva[i].iMesReserva == (iMes)){
                                                            System.out.println(" > RESERVA Nº"+ iNumeroReserva +" \n---------------\n"
                                                                + "Nom: "+reserva[i].sNomReserva +"\n"
                                                                + "Nº Comensals: "+reserva[i].iComensalsR +"\n"
                                                                + "Dia: "+reserva[i].iDiaReserva +"\n"
                                                                + "Mes: "+sMeses[reserva[i].iMesReserva-1] +"\n"
                                                                + "Telefon y numero de registre: "+reserva[i].iTelefon +"\n");
                                                            iNumeroReserva++;
                                                        }
                                                    }break;
                                                }else{
                                                    System.out.println("\033[31m*Mes erroni*\033[30m");
                                                }
                                            }break;
                                        }
                                        
                                        case 2:{ //TOTES LES RESERVES
                                            //Mostra totes les reserves
                                            System.out.println("-----------\n Totes \n------------\n");
                                            iNumeroReserva = 1;
                                            
                                            for(int i = 0;i<1000;i++){
                                                if(reserva[i].iTelefon > 0){
                                                    System.out.println(" > RESERVA Nº"+ iNumeroReserva +" \n---------------\n"
                                                        + "Nom: "+reserva[i].sNomReserva+"\n"
                                                        + "Nº Comensals: "+reserva[i].iComensalsR+"\n"
                                                        + "Dia: "+reserva[i].iDiaReserva+"\n"
                                                        + "Mes: "+sMeses[reserva[i].iMesReserva-1]+"\n"
                                                        + "Telefon y numero de registre: "+reserva[i].iTelefon +"\n");
                                                    iNumeroReserva++;
                                                }
                                            }break;
                                        }
                                        
                                        case 3:{ //TANCAR SESSIÓ
                                            System.out.println("\033[35mAdeu fins la proxima!\033[30m");
                                            bAdmin = false;
                                            break;
                                        }
                                        
                                        default:{ //OPCIÓ INVALIDA
                                            System.out.println("\033[31mHas escollit una opció invalida\033[30m");
                                        }
                                    }
                                }
                            }
                        }
                    }break;
                }
                
                case 2:{ //REGISTAR USUARI
                                        
                    boolean bRegistreC;//Comprovar si el registre s'ha fet correctament
                    int iNRegistres = 0;//Posicio el primer espai buit a la base de dades de l'usuari
                    
                    while(bRegistre == true){
                        System.out.println("------------------\n REGISTRAR USUARI \n------------------");
                        
                        bRegistreC = true;//Registre sempre es correcte
                        System.out.println("Escriu 'adeu' per tornar al menu");
                        System.out.print("Nom d'usuari: ");
                        sUsuari = sc.nextLine();
                        sUsuari = sc.nextLine();
                        //Comprova si l'usuari vol cancel·lar el registre
                        if(sUsuari.equals("adeu")){
                            System.out.println("\033[33mRegistre nou cancel·lat\033[30m");
                            break;
                        }
                        System.out.print("Contrasenya: ");
                        sContrasenya = sc.next();
                        System.out.print("Repeteix contrasenya: ");
                        sContrasenyaRep = sc.next();
                        System.out.print("Correu: ");
                        sCorreu = sc.next();
                        //Comprova si l'usuari ja existeix
                        for(int i = 0;i<sTUsuari.length;i++){
                            if(sUsuari.equals(sTUsuari[i][0])){
                                System.out.println("\033[31mAquest usuari ja existeix\033[30m");
                                bRegistreC = false;//Registre incorrecte
                            }
                            
                        }
                        //Comprova si el correu ja s'esta utlitzant
                        for(int i = 0;i<sTUsuari.length;i++){
                            if(sCorreu.equals(sTUsuari[i][2])){
                                System.out.println("\033[31mAquest correu ja s'esta utilitzant\033[30m");
                                bRegistreC = false;//Registre incorrecte
                            }
                        }
                        //Comprova si les contrasenyes coincideixen
                        if(sContrasenya.equals(sContrasenyaRep)){
                            
                        }else{
                            System.out.println("\033[31mLes contrasenyes no coincideixen\033[30m");
                            bRegistreC = false;//Registre incorrecte
                        }
                        //Comprova que l'usuari es correcte
                        if(bRegistreC == true){
                            //Guarda les dades de l'usuari
                            while(true){
                                if(sTUsuari[iNRegistres][0]==null){//Busca una posicio buida
                                    sTUsuari[iNRegistres][0] = sUsuari;
                                    sTUsuari[iNRegistres][1] = sContrasenya;
                                    sTUsuari[iNRegistres][2] = sCorreu;
                                    sTUsuari[iNRegistres][3] = "usu";
                                    System.out.println("\033[32mS'ha creat l'usuari correctament\033[30m");
                                    break;
                                }
                                iNRegistres++;
                            }
                            bRegistre = false;//Tancamnet del menú de registres
                        }
                    }break;
                }
                
                case 3:{ //RECUPERAR CONTRASENYA
                    
                    bRecuperacio = true;
                    
                    while(bRecuperacio == true){
                        System.out.println("-----------------------\n RECUPERAR CONTRASENYA \n-----------------------");
                    
                        //Comprovar si usuari i correu son corretes
                        boolean bComprovacio = false;//Sempre es fals
                    
                        System.out.println("Escriu 'adeu' per tornar al menu");
                        System.out.print("Nom d'usuari: ");
                        sUsuari = sc.next();
                        //Comprova si l'usuari vol cancel·lar la recuperacio de contrasenya
                        if(sUsuari.equals("adeu")){
                            System.out.println("\033[33mRecuperacio de contrasenya cancel·lada\033[30m");
                            break;
                        }
                        System.out.print("Correu: ");
                        sCorreu = sc.next();
                    
                        //Comprovar si el correu i l'usuari son correctes
                        for(int i = 0;i<sTUsuari.length;i++){
                            if(sUsuari.equals(sTUsuari[i][0])){
                                if(sCorreu.equals(sTUsuari[i][2])){
                                    bComprovacio = true;//Usuari i correu coincideixen
                                    System.out.print("Nova contrasenya: ");
                                    sContrasenya = sc.next();
                                    System.out.print("Repeteix contrasenya: ");
                                    sContrasenyaRep = sc.next();
                                    //Comprova les contrasnyes coincideixen
                                    if(sContrasenya.equals(sContrasenyaRep)){
                                        sTUsuari[i][1] = sContrasenya;
                                        System.out.println("\033[32mContrasenya nova aplicada correctament\033[30m");
                                        bRecuperacio = false;
                                    }else{
                                         System.out.println("\033[31mLes contrasenyes no coincideixen\033[30m");
                                    }
                                }
                            }
                        }
                        //Si l'usuari i correu no conicideixen
                        if(bComprovacio == false){
                            System.out.println("\033[31mUsuari o correu incorrecte\033[30m");
                        }
                    }break;
                }
                //Opcio del menú incorrecte
                default:{
                    System.out.println("\033[31mHas escollit una opció invalida\033[30m");
                }
            }
        }
    }
    
    private static void Menus(String[] Menu){
        for(int i = 0;i<Menu.length;i++){
            System.out.print(Menu[i]);
            iLlegirOpcioMenu();
        }
    }
    private static int iLlegirOpcioMenu(){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Introdueixi una opció: ");
        int iOpcioMenu = sc.nextInt();
        return iOpcioMenu; 
    }
    
    private static String sLlegirOpcioMenu(){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Introdueixi les dades: ");
        String sOpcioMenu = sc.next();
        return sOpcioMenu; 
    }
}