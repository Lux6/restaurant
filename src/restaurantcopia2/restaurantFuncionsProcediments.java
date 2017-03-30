
package restaurantcopia2;

import java.util.Scanner;
import static restaurantcopia2.restaurantBaseDadesMD.*;


class restaurantFuncionsProcediments extends restaurantBaseDadesMD{
        //*Reservar espacio de clases*//
        Scanner sc = new Scanner(System.in);
        TReserva reserva = new TReserva();
        Menus Menus = new Menus();
        Variables var = new Variables();
        BaseDades db = new BaseDades();
        Usuari usu = new Usuari();
        Reserva res = new Reserva();
        sMissatges missatge = new sMissatges();
        bComprobadors comp = new bComprobadors();
        TUsuari tusu = new TUsuari();
    
    
    /**
     * Mostra els menus enmagatzemats al class Menus.java
     * @param Menu 
     */
    public static void Menus(String[] Menu){
        for(int i = 0;i<Menu.length;i++){
            System.out.print(Menu[i]);
        }
    }
    
    /**
     * Mostra una cadena de text
     * @param s 
     */
    public static void Mostra(String s){
        System.out.println(s);
    }
    
    /**
     * Llegeix entrades dels Menus en nombres Int
     * @return 
     */
    public static int iLlegirOpcioMenu(){
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
    public static String sLlegirText(String Text){
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
    public static int sLlegirNumero(String Text){
        Scanner sc = new Scanner(System.in);
        
        int iNumero;
        
        System.out.print(Text);
        iNumero = sc.nextInt();
      
        return iNumero;
    }
    
    /**
     * Retorna missatge de opció invalida
     */
    public static void sOpcioInvalida(){
        System.out.println("\033[31m" + "Has escollit una opció invalida" + "\033[30m");
    }
    
    /**
     * Comproba si el dia introduit esta entre els valors valids (1-29)
     * @param var
     * @param iDiaReserva
     * @param nDiaReserva
     * @return 
     */
    public static void ComprovarDia(Variables var,TReserva reserva,bComprobadors comp){
        if(reserva.iDiaReserva <= 29 && reserva.iDiaReserva > 0){
            System.out.println("Ha escollit el dia "+reserva.iDiaReserva);
            comp.bReserva = false;
        }else{
            System.out.println("\033[31m" + "*Dia erroni*" + "\033[30m");
            comp.bReserva = true;
        }
   
    }
    
    /**
     * Comproba si els comensals introduits estan entre els valors valids (1-10)
     * @param var
     * @param iComensalsR
     * @return 
     */
    public static void ComprovarComensals(Variables var,TReserva reserva,bComprobadors comp){
            if(reserva.iComensalsR <= 11 && reserva.iComensalsR > 0){
                System.out.println("Ha reservat taula per a "+ reserva.iComensalsR);
                comp.bReserva = false;
            }else{ 
                System.out.println("\033[31m" + "*Comensals erronis*" + "\033[30m");
                comp.bReserva = true;
            }
        }
    
    /**
     * Comproba si el telefon introduit esta entre els valors valids (600.000.000 - 999.999.999)
     * @param var
     * @param nTelReserva
     * @param bTelefon
     * @param reserva
     * @return 
     */
    
    /**
     * Comproba si el mes introduit esta entre els valors valids (1-12)
     * @param var
     * @param iMesReserva
     * @param reserva
     * @param nMesReserva
     * @param sMeses
     * @return 
     */
    public static void ComprovarMes(Variables var,TReserva reserva, Menus Menus,bComprobadors comp){
        comp.bCancelarRes = false;

        if(reserva.iMesReserva == 13){//Comprova si es 13 per cancel·lar
            comp.bReserva = false;

        }else if(reserva.iMesReserva <= 12 && reserva.iMesReserva >= 0){
            System.out.println("Ha escollit "+Menus.sMeses[reserva.iMesReserva-1]);
            comp.bReserva = false;

        }else{//Mes introduit erroni
            sOpcioInvalida();
        }
    }
    
    /**
    * Mostra la reserva i retorna un boolea per comprobar si la cerca ha estat correcta o no
    * @param reserva
    * @param sMeses
    * @param var
    * @return 
    */
    public static void MostrarReserva(Variables var,BaseDades db,Reserva res,TReserva reserva,Menus Menus, bComprobadors comp){
        try {
            String sQuery = "SELECT * FROM reserves where telefon= "+reserva.iTelefon+";";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                res.dia = db.rs.getInt("dia");
                res.mes = db.rs.getInt("mes");
                res.comensals = db.rs.getInt("comensals");
                res.nom = db.rs.getString("nom");
                res.telefon = db.rs.getInt("telefon");
                if(reserva.iTelefon == res.telefon){   
                    System.out.println("---------------------\n DADES DE LA RESERVA \n---------------------\n"
                        + "Nom: "+res.nom+"\n"
                        + "Nº Comensals: "+res.comensals+"\n"
                        + "Dia: "+res.dia+"\n"
                        + "Mes: "+Menus.sMeses[res.mes]+"\n"
                        + "Telefon y numero de registre: "+res.telefon);

                    comp.bBuscar = false;
                    break;
                }
            }
            } catch (Exception e) {
        }
        DesconnectarDB(db);
    }
    
    /**
     * Case 3, Busca la reserva del client a traves del seu numero de telefon
     * @param reserva
     * @param sMeses
     * @param var
     * @param err
     * @param Menus 
     */
    public static void BuscarReserva (Variables var,Menus Menus,BaseDades db,Reserva res, TReserva reserva,sMissatges missatge, bComprobadors comp){
        comp.bBuscar = true;  
        
        //Mostra el menu de Buscar Reserva
        Menus(Menus.MenuBuscarReserva);
        
        //Llegeix les dades per buscar reserva
        reserva.iTelefon = sLlegirNumero(missatge.nTelefonReserva);

        //Mostra la Reserva
        MostrarReserva(var, db, res, reserva, Menus, comp);
        //Comprueba si la reserva se ha mostrado, sino muestra mensaje de no encontrada
        if(comp.bBuscar == true){
            Mostra(missatge.sReservaNoTrobada);
        }
    }
    
    public static void EsborrarReserva (Variables var,BaseDades db,TReserva reserva){
        ConnectarDB(db);
        String sQuery = ("DELETE FROM reserves where telefon="+reserva.iTelefon+";");
        UpdateDB(sQuery, db);
        Mostra("\033[32mReserva cancel·lada correctament\033[30m");
        DesconnectarDB(db);
    }
    
    public static void CancelarReserva (Variables var,Menus Menus,BaseDades db,TReserva reserva,sMissatges missatge){
       //Mostra el menu de Cancelar Reserva
        Menus(Menus.MenuCancelarReserva);
        //Llegeix les dades per cancelar reserva
        reserva.iTelefon = sLlegirNumero(missatge.nTelefonReserva);

        EsborrarReserva(var, db,reserva);
    }
    /**
     * Sol·licitar Reserva
     * @param Menus
     * @param reserva
     * @param sMeses
     * @param var 
     */
    public static void IntroduccionComprobacionDatos(Menus Menus,Variables var,BaseDades db,Reserva res,TReserva reserva,sMissatges missatge,bComprobadors comp){
        comp.bCancelarRes = false;
        comp.bReserva = true;
        while(comp.bReserva == true){
            //*Mostra el menu de solicitar reserva*//
            Menus(Menus.MenuSolicitarReserva);

            //Mes de la reserva
            comp.bReserva = true;
            while(comp.bReserva == true){
                reserva.iMesReserva = sLlegirNumero(missatge.nMesReserva);
                ComprovarMes(var, reserva, Menus, comp);
            }

            //Comprova si ha cancel·lat la reserva
            if(reserva.iMesReserva == 13){
                Mostra("\033[33m" + "Sol·licitud de reserva cancel·lada" + "\033[30m");
                comp.bReserva = false;
            }
            
            //Comprova si la reserva continua
            if(comp.bReserva == true){
                
                //Dia de la reserva
                comp.bReserva = true;
                while(comp.bReserva == true){
                    reserva.iDiaReserva = sLlegirNumero(missatge.nDiaReserva);
                    ComprovarDia(var, reserva, comp);
                }

                //Numero de comensals
                comp.bReserva = true;
                while(comp.bReserva == true){
                    reserva.iComensalsR = sLlegirNumero(missatge.nComensalsReserva);
                    ComprovarComensals(var, reserva, comp);
                }

                //Nom de la reserva
                reserva.sNomReserva = sLlegirText(missatge.fNomReserva);//Demana la contrasenya i la desa

                //Numero de telefon
                comp.bReserva = true;
                while(comp.bReserva == true){
                    reserva.iTelefon = sLlegirNumero(missatge.nTelReserva);
                    MD_ComprovarTelefon(var, db, res, missatge, reserva, comp);

                    if(comp.bTelefon == true){
                        break;
                    }else{
                        Mostra("\033[31m" + "Telefon ja existent o invalid!" + "\033[30m");
                    }
                }

                //Mostra i desa les dades de la reserva
                MostarDesarDades(var, db, reserva, Menus);
            }
            comp.bReserva = false;
        }
        
        
        
    } 
    
    /**
     * Sol·licitr Reseva
     * @param var
     * @param db
     * @param sMeses 
     */
    public static void MostarDesarDades(Variables var,BaseDades db,TReserva reserva,Menus Menus){
        Scanner sc = new Scanner(System.in);
        String sConfirmar;                                                                                  //Variables confirmar reserva
        
        /**Mostrar Dades**/
        System.out.println("---------------\nRESUM DE LA RESERVA \n---------------\nNom: "+ reserva.sNomReserva +"\n"
            + "Nº Comensals: "+reserva.iComensalsR+"\n"
            + "Dia: "+reserva.iDiaReserva+"\n"
            + "Mes: "+Menus.sMeses[reserva.iMesReserva-1]+"\n"
            + "Telefon y numero de registre: "+reserva.iTelefon);

        System.out.print("Confirmar (s): ");
        sConfirmar = sc.next();
        
        /**Guardar Dades**/
        if(sConfirmar.equals("s")){
            ConnectarDB(db);
            String sQuery = ("INSERT INTO reserves (dia,mes,comensals,telefon,nom) "
                + "VALUES ("+ reserva.iDiaReserva +','+ reserva.iMesReserva 
                +','+ reserva.iComensalsR +','+ reserva.iTelefon +','+ '\''+reserva.sNomReserva+ '\'' +")");
            UpdateDB(sQuery, db);
            DesconnectarDB(db);
            System.out.println("\033[32mReserva enregistrada correctament\033[30m");
            
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
    public static void ComprovarUsuari(Variables var,BaseDades db,Usuari usu,bComprobadors comp,TUsuari tusu){
        try {
            comp.bUsuariC = false; //L'usuari sempre serà incorrecte
            String sQuery = "SELECT * FROM usuaris;";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                usu.usu = db.rs.getString("nom");
                usu.pass = db.rs.getString("contrasenya");
                usu.tipus = db.rs.getString("tipus");
                if(tusu.sUsuari.equals(usu.usu) && tusu.sContrasenya.equals(usu.pass)){
                    comp.bUsuariC = true;
                    break;
                }
            }
        }  catch (Exception e) {
        }
        DesconnectarDB(db);
    }
    
    public static void SolicitarReserva(Variables var,BaseDades db,Menus Menus,Reserva res,TReserva reserva,sMissatges missatge,bComprobadors comp){
        //Introduccio i comprovacio de Dades    
        IntroduccionComprobacionDatos(Menus, var, db, res, reserva, missatge, comp);
    }
    
    /**
     * Recuperar contrasenya
     * @param var
     * @param sTUsuari 
     */
    public static void ComprovarCorreuUsuari(Variables var,sMissatges missatge,bComprobadors comp,TUsuari tusu){
        for(int i = 0;i<tusu.sTUsuari.length;i++){
            
            if(tusu.sUsuari.equals(tusu.sTUsuari[i][0])){
                
                if(tusu.sCorreu.equals(tusu.sTUsuari[i][2])){
                    
                    comp.bComprovacio = true;//Usuari i correu coincideixen

                    tusu.sContrasenya = sLlegirText(missatge.fNovaContrasenya);
                    tusu.sContrasenyaRep = sLlegirText(missatge.fRepContrasenya);

                    //Comprova les contrasenyes coincideixen
                    if(tusu.sContrasenya.equals(tusu.sContrasenyaRep)){
                        tusu.sTUsuari[i][1] = tusu.sContrasenya;
                        System.out.println("\033[32m" + "Contrasenya nova aplicada correctament" + "\033[30m");
                        comp.bRecuperacio = false;
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
    public static void RecuperarContrasenya(Variables var,Menus Menus,sMissatges missatge,bComprobadors comp,TUsuari tusu){

        while(comp.bRecuperacio == true){
            Menus(Menus.MenuRecuperarContrasenya);

            //Comprovar si usuari i correu son corretes
            boolean bComprovacio = false;//Sempre es fals

            tusu.sUsuari = sLlegirText(missatge.fUsuari);

            //Comprova si l'usuari vol cancel·lar la recuperacio de contrasenya
            if(tusu.sUsuari.equals("adeu")){
                System.out.println("\033[33m" + "Recuperacio de contrasenya cancel·lada" + "\033[30m");
                break;
            }

            tusu.sCorreu = sLlegirText(missatge.fCorreu);

            //Comprovar si el correu i l'usuari son correctes
            ComprovarCorreuUsuari(var, missatge, comp,tusu);

            //Si l'usuari i correu no conicideixen
            if(bComprovacio == false){
                System.out.println("\033[31m" + "Usuari o correu incorrecte" + "\033[30m");
            }
        }
    }
    
    public static void MostarTotesReserves(Variables var,BaseDades db,Reserva res,Menus Menus){
        Mostra("---------------------\n TOTES LES RESERVES \n---------------------\n");
        var.iNumeroReserva = 1;

        try {
            String sQuery = "SELECT * FROM reserves;";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                res.dia = db.rs.getInt("dia");
                res.mes = db.rs.getInt("mes");
                res.comensals = db.rs.getInt("comensals");
                res.nom = db.rs.getString("nom");
                res.telefon = db.rs.getInt("telefon");

                Mostra(" > RESERVA Nº"+ var.iNumeroReserva +" \n---------------\n"
                    + "Nom: "+res.nom+"\n"
                    + "Nº Comensals: "+res.comensals+"\n"
                    + "Dia: "+res.dia+"\n"
                    + "Mes: "+Menus.sMeses[res.mes-1]+"\n"
                    + "Telefon y numero de registre: "+res.telefon +"\n");

                var.iNumeroReserva++;
            }
        }  catch (Exception e) {
        }
        DesconnectarDB(db);
    }
    
    public static void MenuClient(Menus Menus,Variables var,BaseDades db,Reserva res,TReserva reserva,sMissatges missatge,bComprobadors comp){
        while(var.iOpcioSolicitud != 5){    

            //Menu principal
            Menus(Menus.MenuReserves);
            var.iOpcioSolicitud = iLlegirOpcioMenu();

            switch(var.iOpcioSolicitud){
                case 1:{ //*************************************SOLICITAR RESERVA*****************************************************/
                    SolicitarReserva(var, db, Menus, res, reserva, missatge, comp);
                    break;
                } 

                case 2:{ //************************************CANCELAR RESERVA******************************************************//
                    CancelarReserva(var, Menus, db,reserva,missatge);
                    break;
                }

                case 3:{ //*************************************BUSCAR RESERVA******************************************************//
                    BuscarReserva(var, Menus, db, res, reserva, missatge, comp);
                    break;
                }

                case 4:{ //************************************MODIFICAR RESERVA****************************************************//
                    MD_ModificarReserva(var, Menus, db, reserva, res, missatge, comp);

                    break;
                }    
                case 5:{ //TANCA SESSIÓ
                    Mostra(missatge.fComiat);
                    break;
                }

                default:{ //OPCIÓ INVALIDA
                    sOpcioInvalida();
                }
            }
        }
    }
    
    public static void MostrarReservesPerMes(Menus Menus,Variables var,Reserva res,BaseDades db,TReserva reserva,sMissatges missatge){
        int iMes;
        boolean bMostrar = true;
        
        while(bMostrar == true){
            Menus(Menus.MenuReservaPerMes);
            iMes = sLlegirNumero(missatge.nMesReserva);

            if(iMes == 13){//Comprova si l'usuari vol cancel·lar la cerca
                Mostra("\033[33m" + "Busqueda reserva per mes cancel·lada" + "\033[30m");

                bMostrar = false;
            }else if(iMes > 0 && iMes < 13){
                Mostra("-----------\n "+ Menus.sMeses[iMes-1]+ " \n------------\n");

                var.iNumeroReserva = 1;
                try {
                    String sQuery = "SELECT * FROM reserves where mes= "+var+";";
                    ConnectarDB(db);
                    QueryDB(sQuery, db);

                    while (db.rs.next()) {
                        res.dia = db.rs.getInt("dia");
                        res.mes = db.rs.getInt("mes");
                        res.comensals = db.rs.getInt("comensals");
                        res.nom = db.rs.getString("nom");
                        res.telefon = db.rs.getInt("telefon");

                        Mostra(" > RESERVA Nº"+ var.iNumeroReserva +" \n---------------\n"
                            + "Nom: "+res.nom+"\n"
                            + "Nº Comensals: "+res.comensals+"\n"
                            + "Dia: "+res.dia+"\n"
                            + "Mes: "+Menus.sMeses[res.mes-1]+"\n"
                            + "Telefon y numero de registre: "+res.telefon +"\n");

                        var.iNumeroReserva++;
                    }
                }  catch (Exception e) {
                }
                DesconnectarDB(db);

                bMostrar = false;

            }else{
                sOpcioInvalida();
            }
        }
    }
    
    public static void MenuAdministrador(Menus Menus,Variables var,Reserva res,BaseDades db,TReserva reserva,sMissatges missatge){
        boolean bAdmin = true;
        int iGestio = 0;        //Opcions administrador

        while(bAdmin == true){

            Menus(Menus.MenuGestioReserves);
            iGestio = iLlegirOpcioMenu();

            //*****************************************************RESERVES PER MES************************************************//
            switch (iGestio){
                case 1:{ //RESERVA PER MES
                    MostrarReservesPerMes(Menus, var, res, db,reserva,missatge);

                    break;
                }
                //*****************************************************TOTES LES RESERVA*******************************************//
                case 2:{ //MOSTRAR TOTES LES RESERVES
                    MostarTotesReserves(var, db, res, Menus);

                    break;
                }

                case 3:{ //TANCAR SESSIÓ
                    Mostra("\033[35m" + "Adeu fins un altre!" + "\033[30m");
                    bAdmin = false;

                    break;
                }

                default:{ //OPCIÓ INVALIDA
                    sOpcioInvalida();
                }
            }
        }
    }
    
    public static void IniciSessio(Menus Menus,Variables var,BaseDades db,Reserva res,TReserva reserva,Usuari usu,sMissatges missatge,bComprobadors comp,TUsuari tusu){
        boolean bIniciar = true;        //Bucle Iniciar Sessio    
        
        while(bIniciar == true){
            Mostra("----------------\n INICIAR SESSIÓ \n----------------");

            Mostra("Escriu 'adeu' per tornar al menu");
            //Demanar i guardar nom d'usuari
            tusu.sUsuari = sLlegirText(missatge.fUsuari);
            //Comprovar si l'usuari vol cancel·lar l'inici de sessio

            if(tusu.sUsuari.equals("adeu")){
                Mostra("\033[33m" + "Inici de sessió cancel·lat" + "\033[30m");
                break;
            }
            //Demanar i guardar contrasenya
            tusu.sContrasenya = sLlegirText(missatge.fContrasenya);

            //Comprovar usuari
            ComprovarUsuari(var, db, usu, comp,tusu);

            //Mostra missatge depenent si l'usuari s'ha connectat correctament o no
            if(comp.bUsuariC == false){
                Mostra("\033[47m" + "\033[31m" + "Usuari o contrasenya incorrecte" + "\033[30m");
            }else{
                Mostra("\033[47m" + "\033[32m" + "S'ha iniciat la sessió correctament" + "\033[30m");
                //*****************************************************MENU CLIENT************************************************//                         
                if(usu.tipus.equals("usu")){
                    //Menú d'usuari client
                    MenuClient(Menus, var, db, res, reserva, missatge, comp);

                //*****************************************************MENU ADMNNISTRADOR************************************************//
                }else if(usu.tipus.equals("admin")){
                    //Menú d'usauri administrador
                    MenuAdministrador(Menus, var, res, db, reserva, missatge);
                }
            }
        }
    }
}
