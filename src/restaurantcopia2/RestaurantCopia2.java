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
        
        String[] MenuPrincipal = {"------------\n RESTAURANT \n------------\n "
            + "[1] Iniciar Sessio\n "
            + "[2] Registrar-se\n "
            + "[3] Recuperar Contrasenya\n "
            + "[4] Tancar aplicació\n "};
        
        String[] MenuReserves = {"---------------\n MENU RESERVES\n---------------\n "
            + "[1] Sol·licitar reserva\n "
            + "[2] Cancel·lar reserva\n "
            + "[3] Buscar reserva\n "
            + "[4] Modificar reserva\n "
            + "[5] Tancar sessió\n "};
        
        String[] MenuOpcionsModificarReserva = {"Indique que voleu modificar:\n "
            + "[1] Nom\n "
            + "[2] Nº de comensals\n "
            + "[3] Dia\n "
            + "[4] Mes\n "
            + "[5] Tornar al menú\n "};
        
        String[] MenuGestioReserves = {"--------------------\n GESTIO DE RESERVES \n--------------------\n "
            + "[1] Reserves per mes\n "
            + "[2] Totes les reserves\n "
            + "[3] Tancar Sessio\n "};
        
        String[] MenuSolicitarReserva = {"---------------\n SOL·LICITAR RESERVA \n---------------\n"
            + "Introeudix '13' per tornar al menú\n "};
        
        String[] MenuModificarReserva = {"---------------\n MODIFICAR RESERVA \n---------------\n "
            + "Introdueix 13 per cancel·lar la modificacio\n "};
        
        String[] MenuReservaPerMes = {"\n-------------------\n RESERVES PER MES \n-------------------\n "
            + "Introeudix '13' per tornar al menú\n "};
        
        String[] MenuRegistrarUsuari = {"------------------\n REGISTRAR USUARI \n------------------\n "
            + "Escriu 'adeu' per tornar al menu"};
        
        String[] MenuCancelarReserva = {"---------------\n CANCEL·LACIÓ RESERVA \n---------------\n"};
        
        String[] MenuBuscarReserva = {"-----------------\n BUSQUEDA RESERVA \n-----------------\n "
            + "Introeudix '13' per tornar al menú\n "
            + "Introdueixi el numero de telefon amb el que va fer la reserva:\n "};
        
        String[] MenuRecuperarContrasenya= {"-----------------------\n RECUPERAR CONTRASENYA \n-----------------------\n "
            + "Escriu 'adeu' per tornar al menu\n "};
        
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
        
        String fNomReserva = "Introdueixi el nom al que vol fer la reserva:\n -> "; //Text demanar nom reserva
        String fUsuari = "Nom usuari:\n -> "; //Text demanar nom d'usuari
        String fContrasenya = "Contrasenya:\n -> "; //Text demanar contrasenya
        String fNovaContrasenya = "Nova Contrasenya:\n -> "; //Text demanr nova contrasenya (Registrar-se/Recuperar)
        String fRepContrasenya = "Repereix Contrasenya:\n -> "; //Text demanar repetir contrsenya (Registrar usuari)
        String fNouUsuari = "Nou nom d'Usuari:\n -> "; //Text demanr nou nom d'usuari
        String fCorreu = "Introdueix el vostre correu\n -> "; //Text demanar correu (Registrar usuari)
        
        String nMesReserva = "Numero del mes de la reserva:\n -> "; //Text demanar Mes reserva
        String nTelefonReserva = "Introdueixi el numero de telefon amb el que va fer la reserva:\n -> "; //Text demanar Telefon reserva(Visualitzat/Modificar)
        String nDiaReserva = "Introduexi ara el dia desitjat(1-29):\n -> "; //Text demanr dia reserva
        String nComensalsReserva = "Introduexi el numero de comensals(1-11):\n -> "; //Text demanr comensals reserva
        String nTelReserva = "Introduexi el numero de telefon:\n -> "; //Text demanar Telefon reserva (Sol·licitar)

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
        
        
        while(bOP == true){
            
            Menus(MenuPrincipal);
            iOpcioPrincipal = iLlegirOpcioMenu();
            
            //Comprovar si l'usuari vol tancar el programa.
            if(iOpcioPrincipal == 4){
                System.out.println("\033[34m"+"Adeu!");
                break;
            }
            
            switch(iOpcioPrincipal){
                case 1:{ //INICI SESSIÓ
                                        
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
                                    iUsuari = i;
                                }
                            }
                        }
                        
                        //Mostra missatge depenent si l'usuari s'ha connectat correctament o no
                        if(bUsuariC == false){
                            System.out.println("\033[47m" + "\033[31m" + "Usuari o contrasenya incorrecte" + "\033[30m");
                        }else{
                            System.out.println("\033[47m" + "\033[32m" + "S'ha iniciat la sessió correctament" + "\033[30m");
                            //Comprova si l'usuari es un usuari client
                            if(sTUsuari[iUsuari][3].equals("usu")){
                                do{    
                                    //Menu principal
                                    Menus(MenuReserves);
                                    iOpcioSolicitud = iLlegirOpcioMenu();
                    
                                    switch(iOpcioSolicitud){
                                        case 1:{ //SOLICITAR RESERVA
                                            bReserva = true;
                                            
                                            while(true){
                                            
                                                while(true){
                                                    //Mes de la reserva
                                                    Menus(MenuSolicitarReserva);
                                                    iMesReserva = sLlegirNumero(nMesReserva);
                                                    
                                                    if(iMesReserva == 13){//Comprova si es 13 per cancel·lar
                                                        System.out.println("\033[33m" + "Sol·licitud de reserva cancel·lada" + "\033[30m");
                                                        bReserva = false;
                                                    }else if(iMesReserva <= 12 && iMesReserva >= 0){
                                                        System.out.println("Ha escollit "+sMeses[iMesReserva-1]);
                                                        break;
                                                    }else{
                                                        System.out.println("\033[31m" + "*Mes erroni*" + "\033[30m");
                                                    }
                                                    
                                                    //Si ha introdueit un mes valid continua amb la reserva
                                                    if(bReserva == false){break;}
                                                }
                                                
                                                //Si ha cancel·lat la reserva torna al menú
                                                if(bReserva == false){break;}
                            
                                                //Dia de la reserva
                                                while(true){
                                                    iDiaReserva = sLlegirNumero(nDiaReserva);
                        
                                                    if(iDiaReserva <= 29 && iDiaReserva > 0){
                                                        System.out.println("Ha escollit el dia "+iDiaReserva);
                                                        break;
                                                    }else{ 
                                                        System.out.println("\033[31m" + "*Dia erroni*" + "\033[30m");
                                                    }
                      
                                                }
                                                
                            
                                                //Numero de comensals
                                                while(true){
                                                    
                                                    iComensalsR = sLlegirNumero(nComensalsReserva);
                        
                                                    if(iComensalsR <= 11 && iComensalsR > 0){
                                                        System.out.println("Ha reservat taula per a "+ iComensalsR);
                                                        break;
                                                    }else{ 
                                                        System.out.println("\033[31m" + "*Comensals erronis*" + "\033[30m");
                                                    }
                      
                                                }
                                                
                                                //Nom de la reserva
                                                sNomReserva = sLlegirText(fNomReserva);
                    
                                                //Numero de telefon
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
                                            Menus(MenuCancelarReserva);
                                            
                                            iTelefon = sLlegirNumero(nTelefonReserva);
                    
                                            for(int i = 0; i < c; i++){
                                                if(iTelefon==reserva[i].iTelefon){
                                                    reserva[i].iTelefon=0;
                                                    System.out.println("\033[32m" + "Reserva cancel·lada correctament" + "\033[30m");
                                                    reserva[c].iComensalsR = 25;
                                                    reserva[c].iDiaReserva = 30;
                                                    reserva[c].iMesReserva = -1;
                                                    reserva[c].iTelefon = 0;
                                                    break;
                                                }else{
                                                    System.out.println("\033[31m" + "Reserva no trobada" + "\033[30m");
                                                }
                                            }break;
                                        }
                                        
                                        case 3:{ //BUSCAR RESERVA
                                            
                                            iTelefon=sc.nextInt();
                                            
                                            if(iTelefon == 13){ //Comprova si vol cancel·lar la cerca
                                                System.out.println("\033[33m" + "Busqueda de reserva cancel·lada" + "\033[30m");
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
                                                System.out.println("\033[31m" + "Reserva no trobada." + "\033[30m");
                                            }break;
                                        }
                                        case 4:{ //MODIFICAR RESERVA
                                            
                                            bModificarRes = true;
                                            
                                            while(bModificarRes == true){
                                                
                                                Menus(MenuModificarReserva);   
                                                iTelefon = sLlegirNumero(nTelefonReserva);

                                                if(iTelefon == 13){
                                                    System.out.println("\033[33m" + "Modificacio de resrva cancel·lada" + "\033[30m");
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
                                                        
                                                        Menus(MenuOpcionsModificarReserva);
                                                        iOpcioModificar = iLlegirOpcioMenu();
                            
                                                        switch(iOpcioModificar){
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
                                                                    iComensalsR = sLlegirNumero(nComensalsReserva);
                        
                                                                    if(iComensalsR <= 11 && iComensalsR > 0){
                                                                        System.out.println("Ha reservat taula per a "+iComensalsR);
                                                                        reserva[i].iComensalsR = iComensalsR;
                                                                        break;
                                                                    }else{ 
                                                                        System.out.println("\033[31m" + "*Comensals erronis*" + "\033[30m");
                                                                    }
                      
                                                                }break;
                                                            }
                                                            case 3:{ //MODIFICAR DIA
                                                                while(true){
                                                                    System.out.println("Dia seleccionat actualment: "+reserva[i].iDiaReserva);
                                                                    iDiaReserva = sLlegirNumero(nDiaReserva);
                        
                                                                    if(iDiaReserva <= 29 && iDiaReserva > 0){
                                                                        System.out.println("Ha escollit el dia "+ iDiaReserva);
                                                                        reserva[i].iDiaReserva = iDiaReserva;
                                                                        break;
                                                                    }else{ 
                                                                        System.out.println("\033[31m" + "*Dia erroni*" + "\033[30m");
                                                                    }
                                                                }break;
                                                            }
                                                            case 4:{ //MODIFICAR MES
                                                                while(true){
                                                                    System.out.println("Mes seleccionat actualment: "+reserva[i].iMesReserva);
                                                                    iMesReserva = sLlegirNumero(nDiaReserva);
                                                    
                                                                    if(iMesReserva <= 12 && iMesReserva >= 0){
                                                                        System.out.println("Ha escollit "+sMeses[iMesReserva-1]);
                                                                        reserva[i].iMesReserva = iMesReserva;
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
                                                                System.out.println("\033[31m" + "*Opció invalida" + "\033[30m");
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
                                            System.out.println("\033[31m" + "Has escollit una opció invalida" + "\033[30m");
                                        }
                                    }
                                }while(iOpcioSolicitud != 5);
                            //Comprova si es un usuari administrador    
                            }else if(sTUsuari[iUsuari][3].equals("admin")){
                                bAdmin = true;
                                while(bAdmin == true){
                                    
                                    Menus(MenuGestioReserves);
                                    iGestio = iLlegirOpcioMenu();
                                    
                                    
                                    switch (iGestio){
                                        case 1:{ //RESERVA PER MES
                                            while(true){  
                                                //Mostra la reserva per mes
                                                Menus(MenuReservaPerMes);
                                                iMes = sLlegirNumero(nMesReserva);
                                                
                                                if(iMes == 13){//Comprova si l'usuari vol cancel·lar la cerca
                                                    System.out.println("\033[33m" + "Busqueda reserva per mes cancel·lada" + "\033[30m");
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
                                                    System.out.println("\033[31m" + "*Mes erroni*" + "\033[30m");
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
                                            System.out.println("\033[35m" + "Adeu fins la proxima!" + "\033[30m");
                                            bAdmin = false;
                                            break;
                                        }
                                        
                                        default:{ //OPCIÓ INVALIDA
                                            System.out.println("\033[31m" + "Has escollit una opció invalida" + "\033[30m");
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
                        Menus(MenuRegistrarUsuari);
                        
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
                                if(sTUsuari[iNRegistres][0]==null){//Busca una posicio buida
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
                
                case 3:{ //RECUPERAR CONTRASENYA
                    
                    bRecuperacio = true;
                    
                    while(bRecuperacio == true){
                        Menus(MenuRecuperarContrasenya);
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
                                    
                                    //Comprova les contrasnyes coincideixen
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
                    System.out.println("\033[31m" + "Has escollit una opció invalida" + "\033[30m");
                }
            }
        }
    }
    
    private static void Menus(String[] Menu){
        for(int i = 0;i<Menu.length;i++){
            System.out.print(Menu[i]);
        }
    }
    private static int iLlegirOpcioMenu(){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Introdueixi una opció:\n -> ");
        int iOpcioMenu = sc.nextInt();
        
        return iOpcioMenu; 
    }
    
    private static String sLlegirOpcioMenu(){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Introdueixi les dades:\n -> ");
        String sOpcioMenu = sc.next();
        
        return sOpcioMenu; 
    }
    
    private static String sLlegirText(String Text){
        Scanner sc = new Scanner(System.in);
        
        String sLectura;
        
        System.out.print(Text);
        sLectura = sc.nextLine();
        
        return sLectura;
    }
    
    private static int sLlegirNumero(String Text){
        Scanner sc = new Scanner(System.in);
        
        int iNumero;
        
        System.out.print(Text);
        iNumero = sc.nextInt();
        
        return iNumero;
    }
}