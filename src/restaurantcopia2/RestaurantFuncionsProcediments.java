
package restaurantcopia2;

import java.util.Scanner;
import static restaurantcopia2.RestaurantBaseDadesMD.*;


class RestaurantFuncionsProcediments extends RestaurantBaseDadesMD{
        //*Reservar espacio de clases*//
        Scanner sc = new Scanner(System.in);
        TReserva reserva = new TReserva();
        Menus Menus = new Menus();
        Opcions ops = new Opcions();
        BaseDades db = new BaseDades();
        DBUsuari dbusu = new DBUsuari();
        DBReserva dbres = new DBReserva();
        sMissatges missatge = new sMissatges();
        bComprobadors comp = new bComprobadors();
        TUsuari tusu = new TUsuari();
    
    
    /**
     * Inicia la sessió i comprova si es client o administratiu
     * @param Menus
     * @param ops
     * @param db
     * @param dbres
     * @param reserva
     * @param usu
     * @param missatge
     * @param comp
     * @param tusu 
     */
    public static void IniciSessio(Menus Menus,Opcions ops,BaseDades db,DBReserva dbres,TReserva reserva,DBUsuari usu,sMissatges missatge,bComprobadors comp,TUsuari tusu){
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
            ComprovarUsuari(db, usu, comp, tusu);

            //Mostra missatge depenent si l'usuari s'ha connectat correctament o no
            if(comp.bUsuariC == false){
                Mostra("\033[47m" + "\033[31m" + "Usuari o contrasenya incorrecte" + "\033[30m");
            }else{
                Mostra("\033[47m" + "\033[32m" + "S'ha iniciat la sessió correctament" + "\033[30m");
                //*****************************************************MENU CLIENT************************************************//                         
                if(usu.tipus.equals("usu")){
                    //Menú d'usuari client
                    MenuClient(Menus, ops, db, dbres, reserva, missatge, comp);

                //*****************************************************MENU ADMNNISTRADOR************************************************//
                }else if(usu.tipus.equals("admin")){
                    //Menú d'usauri administrador
                    MenuAdministrador(Menus, ops, dbres, db, missatge);
                }
            }
        }
    }

    /**
     * Menú de l'usuari client
     * @param Menus
     * @param ops
     * @param db
     * @param dbres
     * @param reserva
     * @param missatge
     * @param comp 
     */
    public static void MenuClient(Menus Menus,Opcions ops,BaseDades db,DBReserva dbres,TReserva reserva,sMissatges missatge,bComprobadors comp){
        while(ops.iOpcioSolicitud != 5){    

            //Menu principal
            Menus(Menus.MenuReserves);
            ops.iOpcioSolicitud = iLlegirOpcioMenu();

            switch(ops.iOpcioSolicitud){
                case 1:{ //*************************************SOLICITAR RESERVA*****************************************************/
                    SolicitarReserva(db, Menus, dbres, reserva, missatge, comp);
                    break;
                } 

                case 2:{ //************************************CANCELAR RESERVA******************************************************//
                    CancelarReserva(Menus, db, reserva, missatge);
                    break;
                }

                case 3:{ //*************************************BUSCAR RESERVA******************************************************//
                    BuscarReserva(Menus, db, dbres, reserva, missatge, comp);
                    break;
                }

                case 4:{ //************************************MODIFICAR RESERVA****************************************************//
                    MD_ModificarReserva(ops, Menus, db, reserva, dbres, missatge, comp);

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
    
    /**
     * Solicitar Reserva
     * @param var
     * @param db
     * @param Menus
     * @param dbres
     * @param reserva
     * @param missatge
     * @param comp 
     */
    public static void SolicitarReserva(BaseDades db,Menus Menus,DBReserva dbres,TReserva reserva,sMissatges missatge,bComprobadors comp){
        //Introduccio i comprovacio de Dades    
        IntroduccionComprobacionDatos(Menus, db, dbres, reserva, missatge, comp);
    }
        
    /**
    * Introducció dels valors de la reserva
    * @param Menus
    * @param var
    * @param db
    * @param dbres
    * @param reserva
    * @param missatge
    * @param comp 
    */
    public static void IntroduccionComprobacionDatos(Menus Menus,BaseDades db,DBReserva dbres,TReserva reserva,sMissatges missatge,bComprobadors comp){
        comp.bCancelarRes = false;
        comp.bReserva = true;
        while(comp.bReserva == true){
            //*Mostra el menu de solicitar reserva*//
            Menus(Menus.MenuSolicitarReserva);

            //Mes de la reserva
            comp.bReserva = true;
            while(comp.bReserva == true){
                reserva.iMesReserva = sLlegirNumero(missatge.nMesReserva);
                ComprovarMes(reserva, Menus, comp);
            }

            //Comprova si ha cancel·lat la reserva
            if(reserva.iMesReserva == 13){
                Mostra("\033[33m" + "Sol·licitud de reserva cancel·lada" + "\033[30m");
                comp.bReserva = true;
            }
            
            //Comprova si la reserva continua
            if(comp.bCancelarRes == false){
                
                //Dia de la reserva
                comp.bReserva = true;
                while(comp.bReserva == true){
                    reserva.iDiaReserva = sLlegirNumero(missatge.nDiaReserva);
                    ComprovarDia(reserva, comp);
                }

                //Numero de comensals
                comp.bReserva = true;
                while(comp.bReserva == true){
                    reserva.iComensalsR = sLlegirNumero(missatge.nComensalsReserva);
                    ComprovarComensals(reserva, comp);
                }

                //Nom de la reserva
                reserva.sNomReserva = sLlegirText(missatge.fNomReserva);//Demana la contrasenya i la desa

                //Numero de telefon
                comp.bReserva = true;
                while(comp.bReserva == true){
                    comp.bUTelefon = true;
                    reserva.iTelefon = sLlegirNumero(missatge.nTelReserva);
                    ComprovarTelefon(db, dbres, reserva, comp);

                    if(comp.bTelefon == true){
                        comp.bTelefon = false;
                    }else if (comp.bUTelefon == false){
                        Mostra("\033[31m" + "Telefon ja existent o invalid!" + "\033[30m");
                    }
                }

                //Mostra i desa les dades de la reserva
                MostarDesarDades(db, reserva, Menus);
            }
            comp.bReserva = false;
        }
        
        
        
    } 
    
    /**
     * Mostra les dades de la reserva i les guarda en cas de confirmar la reserva.
     * @param var
     * @param db
     * @param sMeses 
     */
    public static void MostarDesarDades(BaseDades db,TReserva reserva,Menus Menus){
        Scanner sc = new Scanner(System.in);
        String sConfirmar;                                                                                  //Variables confirmar reserva
        
        /**Mostrar Dades**/
        System.out.println("---------------\nRESUM DE LA RESERVA \n---------------\nNom: "+ reserva.sNomReserva +"\n"
            + "Nº Comensals: "+reserva.iComensalsR+"\n"
            + "Dia: "+reserva.iDiaReserva+"\n"
            + "Mes: "+Menus.sMeses[reserva.iMesReserva-1]+"\n"
            + "Telefon y numero de registre: "+reserva.iTelefon);

        sConfirmar = sLlegirText("Confirmar (s): ");
        
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
     * Cancella una reserva previa esborrant l'entrada
     * @param var
     * @param Menus
     * @param db
     * @param reserva
     * @param missatge 
     */
    public static void CancelarReserva (Menus Menus,BaseDades db,TReserva reserva,sMissatges missatge){
       //Mostra el menu de Cancelar Reserva
        Menus(Menus.MenuCancelarReserva);
        //Llegeix les dades per cancelar reserva
        reserva.iTelefon = sLlegirNumero(missatge.nTelefonReserva);

        EsborrarReserva(db, reserva);
    }
    
    /**
     * Elimina la reserva
     * @param var
     * @param db
     * @param reserva 
     */
    public static void EsborrarReserva (BaseDades db,TReserva reserva){
        ConnectarDB(db);
        String sQuery = ("DELETE FROM reserves where telefon="+reserva.iTelefon+";");
        UpdateDB(sQuery, db);
        Mostra("\033[32mReserva cancel·lada correctament\033[30m");
        DesconnectarDB(db);
    }
    
    /**
     * Case 3, Busca la reserva del client a traves del seu numero de telefon
     * @param var
     * @param Menus
     * @param db
     * @param dbres
     * @param reserva
     * @param missatge
     * @param comp 
     */
    public static void BuscarReserva (Menus Menus,BaseDades db,DBReserva dbres, TReserva reserva,sMissatges missatge, bComprobadors comp){
        comp.bBuscar = true;  
        
        //Mostra el menu de Buscar Reserva
        Menus(Menus.MenuBuscarReserva);
        
        //Llegeix les dades per buscar reserva
        reserva.iTelefon = sLlegirNumero(missatge.nTelefonReserva);

        //Mostra la Reserva
        MostrarReserva(db, dbres, reserva, Menus, comp);
        
        //Comprueba si la reserva se ha mostrado, sino muestra mensaje de no encontrada
        if(comp.bBuscar == true){
            Mostra(missatge.sReservaNoTrobada);
        }
    }
    
    /**
     *Mostra la reserva i retorna un boolea per comprobar si la cerca ha estat correcta o no
     * @param var
     * @param db
     * @param dbres
     * @param reserva
     * @param Menus
     * @param comp 
     */
    public static void MostrarReserva(BaseDades db,DBReserva dbres,TReserva reserva,Menus Menus, bComprobadors comp){
        try {
            String sQuery = "SELECT * FROM reserves where telefon= "+reserva.iTelefon+";";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                dbres.dia = db.rs.getInt("dia");
                dbres.mes = db.rs.getInt("mes");
                dbres.comensals = db.rs.getInt("comensals");
                dbres.nom = db.rs.getString("nom");
                dbres.telefon = db.rs.getInt("telefon");
                if(reserva.iTelefon == dbres.telefon){   
                    System.out.println("---------------------\n DADES DE LA RESERVA \n---------------------\n"
                        + "Nom: "+dbres.nom+"\n"
                        + "Nº Comensals: "+dbres.comensals+"\n"
                        + "Dia: "+dbres.dia+"\n"
                        + "Mes: "+Menus.sMeses[dbres.mes]+"\n"
                        + "Telefon y numero de registre: "+dbres.telefon);

                    comp.bBuscar = false;
                    break;
                }
            }
            } catch (Exception e) {
        }
        DesconnectarDB(db);
    }
       
    /**
     * Menú de l'usuari administrador
     * @param Menus
     * @param var
     * @param dbres
     * @param db
     * @param reserva
     * @param missatge 
     */
    public static void MenuAdministrador(Menus Menus,Opcions ops,DBReserva dbres,BaseDades db,sMissatges missatge){
        boolean bAdmin = true;
        int iGestio = 0;        //Opcions administrador

        while(bAdmin == true){

            Menus(Menus.MenuGestioReserves);
            iGestio = iLlegirOpcioMenu();

            //*****************************************************RESERVES PER MES************************************************//
            switch (iGestio){
                case 1:{ //RESERVA PER MES
                    MostrarReservesPerMes(Menus, dbres, db, missatge);

                    break;
                }
                //*****************************************************TOTES LES RESERVA*******************************************//
                case 2:{ //MOSTRAR TOTES LES RESERVES
                    MostarTotesReserves(db, dbres, Menus);

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
    
    /**
     * Mostra totes les reserves
     * @param var
     * @param db
     * @param dbres
     * @param Menus 
     */
    public static void MostarTotesReserves(BaseDades db,DBReserva dbres,Menus Menus){
        Mostra("---------------------\n TOTES LES RESERVES \n---------------------\n");
        int iNumeroReserva = 0;

        try {
            String sQuery = "SELECT * FROM reserves;";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                dbres.dia = db.rs.getInt("dia");
                dbres.mes = db.rs.getInt("mes");
                dbres.comensals = db.rs.getInt("comensals");
                dbres.nom = db.rs.getString("nom");
                dbres.telefon = db.rs.getInt("telefon");

                Mostra(" > RESERVA Nº"+ iNumeroReserva +" \n---------------\n"
                    + "Nom: "+dbres.nom+"\n"
                    + "Nº Comensals: "+dbres.comensals+"\n"
                    + "Dia: "+dbres.dia+"\n"
                    + "Mes: "+Menus.sMeses[dbres.mes-1]+"\n"
                    + "Telefon y numero de registre: "+dbres.telefon +"\n");

                iNumeroReserva++;
            }
        }  catch (Exception e) {
        }
        DesconnectarDB(db);
    }
    
    /**
     * Mostra les reserves de cada mes
     * @param Menus
     * @param var
     * @param dbres
     * @param db
     * @param reserva
     * @param missatge 
     */
    public static void MostrarReservesPerMes(Menus Menus,DBReserva dbres,BaseDades db,sMissatges missatge){
        int iMes;
        int iNumeroReserva = 0;
        boolean bMostrar = true;
        
        while(bMostrar == true){
            Menus(Menus.MenuReservaPerMes);
            iMes = sLlegirNumero(missatge.nMesReserva);

            if(iMes == 13){//Comprova si l'usuari vol cancel·lar la cerca
                Mostra("\033[33m" + "Busqueda reserva per mes cancel·lada" + "\033[30m");

                bMostrar = false;
            }else if(iMes > 0 && iMes < 13){
                Mostra("-----------\n "+ Menus.sMeses[iMes-1]+ " \n------------\n");

                iNumeroReserva = 1;
                try {
                    String sQuery = "SELECT * FROM reserves where mes= "+iMes+";";
                    ConnectarDB(db);
                    QueryDB(sQuery, db);

                    while (db.rs.next()) {
                        dbres.dia = db.rs.getInt("dia");
                        dbres.mes = db.rs.getInt("mes");
                        dbres.comensals = db.rs.getInt("comensals");
                        dbres.nom = db.rs.getString("nom");
                        dbres.telefon = db.rs.getInt("telefon");

                        Mostra(" > RESERVA Nº"+ iNumeroReserva +" \n---------------\n"
                            + "Nom: "+dbres.nom+"\n"
                            + "Nº Comensals: "+dbres.comensals+"\n"
                            + "Dia: "+dbres.dia+"\n"
                            + "Mes: "+Menus.sMeses[dbres.mes-1]+"\n"
                            + "Telefon y numero de registre: "+dbres.telefon +"\n");

                        iNumeroReserva++;
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
    
    /**
     * Comprova si l'usuari existeix
     * @param var
     * @param db
     * @param dbusu 
     */
    public static void ComprovarUsuari(BaseDades db,DBUsuari dbusu,bComprobadors comp,TUsuari tusu){
        try {
            comp.bUsuariC = false; //L'usuari sempre serà incorrecte
            String sQuery = "SELECT * FROM usuaris;";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                dbusu.usu = db.rs.getString("nom");
                dbusu.pass = db.rs.getString("contrasenya");
                dbusu.tipus = db.rs.getString("tipus");
                if(tusu.sUsuari.equals(dbusu.usu) && tusu.sContrasenya.equals(dbusu.pass)){
                    comp.bUsuariC = true;
                    break;
                }
            }
        }  catch (Exception e) {
        }
        DesconnectarDB(db);
    }
         
}
