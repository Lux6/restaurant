package restaurantcopia2;

import java.sql.DriverManager;
import static restaurantcopia2.RestaurantProcedimentsBasics.*;

class RestaurantBaseDadesMD extends RestaurantProcedimentsBasics{
    
    /**
     * Connexió amb la base de dades
     * @param db 
     */
    public static void ConnectarDB(BaseDades db){
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

            db.stmt = db.con.createStatement();  
            
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    /**
     * Mostra una dada seleccionada de la DB
     * @param sQuery
     * @param db 
     */
    public static void QueryDB(String sQuery,BaseDades db){
        try {
            db.rs = db.stmt.executeQuery(sQuery);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    /**
     * Actualitza,esborra o inserta una nova dada
     * @param sQuery
     * @param db 
     */
    public static void UpdateDB(String sQuery,BaseDades db){
        try {
            db.stmt.executeUpdate(sQuery);
            db.con.setAutoCommit(true);
            db.con.commit();
        } catch (Exception e) {
        }
    }
    
    /**
     * Desconnexió amb la Db
     * @param db 
     */
    public static void DesconnectarDB(BaseDades db){
        try{
            db.stmt.close();
            db.con.close();
        } catch (Exception e){
        }
    }
    
    /**Registre un usuari nou a la base de dades 
     * 
     * @param ops
     * @param Menus
     * @param db 
     */
    public static void RG_RegistreUsuari(Opcions ops,Menus Menus,BaseDades db,sMissatges missatge, bComprobadors comp,TUsuari tusu,DBUsuari dbusu){
                     comp.bRegistre = true;                  
                    
                    while(comp.bRegistre == true){
                        Menus(Menus.MenuRegistrarUsuari);
                        
                        comp.bRegistreC = true;//Registre sempre es correcte
                        
                        tusu.sUsuari = sLlegirText(missatge.fUsuari);
                        
                        //Comprova si l'usuari vol cancel·lar el registre
                        if(tusu.sUsuari.equals("adeu")){
                            System.out.println("\033[33m" + "Registre nou cancel·lat" + "\033[30m");
                            break;
                        }
                        
                        tusu.sContrasenya = sLlegirText(missatge.fContrasenya);
                        tusu.sContrasenyaRep = sLlegirText(missatge.fRepContrasenya);
                        tusu.sCorreu = sLlegirText(missatge.fCorreu);
                        
                        //Comprova si l'usuari ja existeix
                        RG_ComprovaUsuari(comp, tusu, db, dbusu);
                        
                        //Comprova si el correu ja s'esta utlitzant
                        RG_ComprovaCorreu(comp, tusu, dbusu, db);
                        
                        //Comprova si les contrasenyes coincideixen
                        RG_ComprovaContrasenya(comp, tusu);
                        
                        //Comprova que l'usuari es correcte
                        RG_GuardaUsuari(db, comp, tusu);
                    
                    }
    }
    
    /**
     * Registra usuari, comprova si l'usuari existeix.
     * @param var
     * @param comp
     * @param tusu
     * @param db
     * @param dbusu 
     */
    public static void RG_ComprovaUsuari(bComprobadors comp,TUsuari tusu,BaseDades db,DBUsuari dbusu){
        try {
            String sQuery = "SELECT * FROM usuaris;";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                dbusu.usu = db.rs.getString("nom");
                dbusu.pass = db.rs.getString("contrasenya");
                dbusu.mail = db.rs.getString("correu");
                dbusu.tipus = db.rs.getString("tipus");

                if(tusu.sUsuari.equals(dbusu.usu)){
                    Mostra("\033[31m" + "Aquest usuari ja existeix" + "\033[30m");
                    comp.bRegistreC = false;

                    break;
                }
            }
        }  catch (Exception e) {
        }
        DesconnectarDB(db);
    }
    
    /**
     * Registrar Usuari, comprova si el correu existeix
     * @param var
     * @param comp
     * @param tusu
     * @param dbusu
     * @param db 
     */
    private static void RG_ComprovaCorreu(bComprobadors comp,TUsuari tusu,DBUsuari dbusu,BaseDades db){
        try {
            String sQuery = "SELECT * FROM usuaris;";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                dbusu.usu = db.rs.getString("nom");
                dbusu.pass = db.rs.getString("contrasenya");
                dbusu.mail = db.rs.getString("correu");
                dbusu.tipus = db.rs.getString("tipus");

                if(tusu.sCorreu.equals(dbusu.mail)){
                    Mostra("\033[31m" + "Aquest correu ja s'esta utilitzant" + "\033[30m");
                    comp.bRegistreC = false;

                    break;
                }
            }
        }  catch (Exception e) {
        }
        DesconnectarDB(db);
    }
    
    /**
     * Registre usuari, comprova si les contrasenyes coincideixen
     * @param var
     * @param comp
     * @param tusu 
     */
    private static void RG_ComprovaContrasenya(bComprobadors comp,TUsuari tusu){
        if(tusu.sContrasenya.equals(tusu.sContrasenyaRep)){
        }else{
            System.out.println("\033[31m" + "Les contrasenyes no coincideixen" + "\033[30m");
            comp.bRegistreC = false;//Registre incorrecte
        }
    }
    
    /**
     * Guarda a la base de dades el nou usuari
     * @param var
     * @param db
     * @param comp
     * @param tusu 
     */
    private static void RG_GuardaUsuari(BaseDades db, bComprobadors comp,TUsuari tusu){
        ConnectarDB(db);
        String sQuery = ("INSERT INTO usuaris (nom,contrasenya,correu,tipus) "
            + "VALUES ("+'\''+tusu.sUsuari +'\''+','+'\''+ tusu.sContrasenya +'\''+','+'\''+ tusu.sCorreu +'\''+',' + "'usu'" +")");
        UpdateDB(sQuery, db);
        Mostra("\033[32mReserva enregistrada correctament\033[30m");
        DesconnectarDB(db);
         comp.bRegistre = false;//Tancamnet del menú de registres
    }
    
    /**
     * Menú modificar reserva.
     * @param var
     * @param dbres
     * @param db
     * @param Menus
     * @param reserva
     * @param missatge
     * @param comp 
     */
    private static void MD_MenuModificar(Opcions var,DBReserva dbres,BaseDades db,Menus Menus,TReserva reserva,sMissatges missatge,bComprobadors comp){
        while(comp.bModificarOps == true){
            Mostra("---------------\nDADES DE LA RESERVA\n---------------\n"
            + "Nom: " + dbres.nom + "\n"
            + "Nº Comensals: "+ dbres.comensals + "\n"
            + "Dia: " + dbres.dia + "\n"
            + "Mes: " + Menus.sMeses[dbres.dia-1] + "\n"
            + "Telefon y numero de registre: " + dbres.telefon);


            Menus(Menus.MenuOpcionsModificarReserva);
            var.iOpcioModificar = iLlegirOpcioMenu();

            switch(var.iOpcioModificar){
                case 1:{ //MODIFICAR NOM
                    MD_ModificarNom(db, dbres, reserva, missatge);

                    break;
                }
                case 2:{ //MODIFICAR COMENSALS
                    MD_ModificarComensals(dbres, db, reserva, missatge, comp);

                    break;
                }
                case 3:{ //MODIFICAR DIA
                    MD_ModificarDia(dbres, db, reserva, missatge, comp);

                    break;
                }
                case 4:{ //MODIFICAR MES
                    MD_ModificarMes(dbres, db, reserva, missatge, Menus, comp);

                    break;
                }
                case 5:{ //TORNAR AL MENÚ
                    comp.bModificarOps = false;
                    break;
                }
                default :{ //OPCIÓ INVALIDA
                    sOpcioInvalida();
                }
            }
        }
    }
    
    /**
     * Modifcar nom de la reserva
     * @param var
     * @param db
     * @param dbres
     * @param reserva
     * @param missatge 
     */
    private static void MD_ModificarNom(BaseDades db,DBReserva dbres,TReserva reserva,sMissatges missatge){
        Mostra("Nom actual "+ dbres.nom);

        reserva.sNomReserva = sLlegirText(missatge.fNouUsuari);

        Mostra("Nom modificat a " + reserva.sNomReserva);

        ConnectarDB(db);
        String sQuery = "UPDATE reserves set nom = \'" + reserva.sNomReserva + "\' where telefon = \'" + dbres.telefon + "\'";
        UpdateDB(sQuery, db);
        DesconnectarDB(db);
    }
    
    /**
     * Modificar comensals de la reserva
     * @param var
     * @param dbres
     * @param db
     * @param reserva
     * @param missatge
     * @param comp 
     */
    private static void MD_ModificarComensals(DBReserva dbres,BaseDades db,TReserva reserva,sMissatges missatge,bComprobadors comp){
        comp.bReserva = true;
        while(comp.bReserva == true){
            Mostra("Nº actual de comensals: "+dbres.comensals);
            reserva.iComensalsR = sLlegirNumero(missatge.nComensalsReserva);
            ComprovarComensals(reserva, comp);

            if(comp.bReserva == false){
                ConnectarDB(db);
                String sQuery = "UPDATE reserves set comensals = \'" + reserva.iComensalsR + "\' where telefon = \'" + dbres.telefon + "\'";
                UpdateDB(sQuery, db);
                DesconnectarDB(db);
            }
        }
    }
    
    /**
     * Modificar dia de la reserva
     * @param var
     * @param dbres
     * @param db
     * @param reserva
     * @param missatge
     * @param comp 
     */
    private static void MD_ModificarDia(DBReserva dbres,BaseDades db,TReserva reserva,sMissatges missatge,bComprobadors comp){
        comp.bReserva = true;
        while(comp.bReserva == true){
            Mostra("Dia seleccionat actualment: "+dbres.dia);
            reserva.iDiaReserva = sLlegirNumero(missatge.nDiaReserva);
            ComprovarDia(reserva, comp);

            if(comp.bReserva == false){
                ConnectarDB(db);
                String sQuery = "UPDATE reserves set dia = \'" + reserva.iDiaReserva + "\' where telefon = \'" + dbres.telefon + "\'";
                UpdateDB(sQuery, db);
                DesconnectarDB(db);

                break;
            }
        }
    }
    
    /**
     * Modificar mes de la reserva
     * @param var
     * @param dbres
     * @param db
     * @param reserva
     * @param missatge
     * @param Menus
     * @param comp 
     */
    private static void MD_ModificarMes(DBReserva dbres,BaseDades db,TReserva reserva,sMissatges missatge,Menus Menus,bComprobadors comp){
        comp.bReserva = true;
        while(comp.bReserva == true){
            
            Mostra("Mes seleccionat actualment: "+dbres.mes);            
            reserva.iMesReserva = sLlegirNumero(missatge.nMesReserva);
            ComprovarMes(reserva, Menus, comp);
            
            if(comp.bReserva == false){
                ConnectarDB(db);
                String sQuery = "UPDATE reserves set mes = \'" + reserva.iMesReserva + "\' where telefon = \'" + dbres.telefon + "\'";
                UpdateDB(sQuery, db);
                DesconnectarDB(db);
            }
        }
    }
    
    /**
     * Modificar reserva
     * @param var
     * @param Menus
     * @param db
     * @param reserva
     * @param dbres
     * @param missatge
     * @param comp 
     */
    public static void MD_ModificarReserva(Opcions var,Menus Menus,BaseDades db, TReserva reserva,DBReserva dbres,sMissatges missatge,bComprobadors comp){
        boolean bModificarRes = true;
        comp.bModificarOps = false;

        Menus(Menus.MenuModificarReserva);

        while(bModificarRes == true){

            reserva.iTelefon = sLlegirNumero(missatge.nTelefonReserva);

            if(reserva.iTelefon == 13){
                Mostra("\033[33m" + "Modificacio de reserva cancel·lada" + "\033[30m");
                break;
            }

            ComprovarTelefon(db, dbres, reserva, comp);

            if(bModificarRes == true){
                Mostra("\033[31m" + "Telefon no trobat" + "\033[30m");
            }

        }

        if(comp.bModificarOps == true){
            MD_MenuModificar(var, dbres, db, Menus, reserva, missatge, comp);
        }
    }

    /**
     * Comprova si el telefon existeix i si es un numero valid
     * @param var
     * @param db
     * @param dbres
     * @param missatge
     * @param reserva
     * @param comp 
     */
    public static void ComprovarTelefon(BaseDades db,DBReserva dbres,TReserva reserva,bComprobadors comp){
        comp.bTelefon = true;
        
        if(reserva.iTelefon < 1000000000 && reserva.iTelefon > 600000000){
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

                    if(reserva.iTelefon == dbres.telefon){
                        comp.bModificarOps = true;
                        comp.bTelefon = false;
                        comp.bUTelefon = false;

                        break;
                    }
                }
            }  catch (Exception e) {
            }
            DesconnectarDB(db);
        }else if(comp.bTelefon == true && comp.bUTelefon == true){
            Mostra("\033[31m" + "Numero de telefon invalid!" + "\033[30m");
            comp.bTelefon = false;
        }
    }
    
}
