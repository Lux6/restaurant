/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantcopia2;

import java.sql.DriverManager;
import static restaurantcopia2.restaurantFuncionsProcediments.*;

/**
 *
 * @author jerje
 */
public final class restaurantBaseDadesMD extends Menus{
    
    
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
            System.out.println("Opened database successfully");

            db.stmt = db.con.createStatement();  
            
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    public static void QueryDB(String sQuery,BaseDades db){
        try {
            db.rs = db.stmt.executeQuery(sQuery);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    public static void UpdateDB(String sQuery,BaseDades db){
        try {
            db.stmt.executeUpdate(sQuery);
            db.con.setAutoCommit(true);
            db.con.commit();
        } catch (Exception e) {
        }
    }
    
    public static void DesconnectarDB(BaseDades db){
        try{
            db.stmt.close();
            db.con.close();
            System.out.println("restaurantcopia2.RestaurantCopia2.DesconnectarDB()");
        } catch (Exception e){
        }
    }
    
    /**Registre un usuari nou a la base de dades 
     * 
     * @param var
     * @param Menus
     * @param db 
     */
    public void RG_RegistreUsuari(Variables var,Menus Menus,BaseDades db){
                     var.bRegistre = true;                  
                    
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
                        RG_ComprovaUsuari(var);
                        
                        //Comprova si el correu ja s'esta utlitzant
                        RG_ComprovaCorreu(var);
                        
                        //Comprova si les contrasenyes coincideixen
                        RG_ComprovaContrasenya(var);
                        
                        //Comprova que l'usuari es correcte
                        RG_GuardaUsuari(var, db);
                    
                    }
    }
    
    public static void RG_ComprovaUsuari(Variables var){
        for(int i = 0; i < var.sTUsuari.length; i++){
            if(var.sUsuari.equals(var.sTUsuari[i][0])){
                Mostra("\033[31m" + "Aquest usuari ja existeix" + "\033[30m");
                var.bRegistreC = false;//Registre incorrecte
            }
        }   
    }
    
    private static void RG_ComprovaCorreu(Variables var){
        for(int i = 0; i < var.sTUsuari.length; i++){
            if(var.sCorreu.equals(var.sTUsuari[i][2])){
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
    
    private static void RG_GuardaUsuari(Variables var,BaseDades db){
        ConnectarDB(db);
        String sQuery = ("INSERT INTO usuaris (nom,contrasenya,correu,tipus) "
            + "VALUES ("+'\''+var.sUsuari +'\''+','+'\''+ var.sContrasenya +'\''+','+'\''+ var.sCorreu +'\''+',' + "'usu'" +")");
        UpdateDB(sQuery, db);
        Mostra("\033[32mReserva enregistrada correctament\033[30m");
        DesconnectarDB(db);
         var.bRegistre = false;//Tancamnet del menú de registres
    }
    
    public static void MD_ComprovarTelefon(Variables var,BaseDades db,Reserva res){
        var.bModificarRes = true;
        var.bTelefon = true;
        
        if(var.iTelefon < 1000000000 && var.iTelefon > 600000000){
            try {
                String sQuery = "SELECT * FROM reserves;";
                ConnectarDB(db);
                QueryDB(sQuery, db);

                while (db.rs.next()) {
                    res.dia = db.rs.getInt("dia");
                    res.mes = db.rs.getInt("mes");
                    res.comensals = db.rs.getInt("comensals");
                    res.nresserva = db.rs.getString("nom");
                    res.telefon = db.rs.getInt("telefon");

                    if(var.iTelefon == res.telefon){
                        var.bModificarRes = false;
                        var.bModificarOps = true;
                        var.bTelefon = false;

                        break;
                    }
                }
            }  catch (Exception e) {
            }
            DesconnectarDB(db);
        }else{
            Mostra("\033[31m" + "Numero de telefon invalid!" + "\033[30m");
            var.bTelefon = false;
        }
    }
    
    private static void MD_MenuModificar(Variables var,Reserva res,BaseDades db,Menus Menus,TReserva reserva){
        while(var.bModificarOps == true){
            Mostra("---------------\nDADES DE LA RESERVA\n---------------\n"
            + "Nom: " + res.nresserva + "\n"
            + "Nº Comensals: "+ res.comensals + "\n"
            + "Dia: " + res.dia + "\n"
            + "Mes: " + var.sMeses[res.dia-1] + "\n"
            + "Telefon y numero de registre: " + res.telefon);


            Menus(Menus.MenuOpcionsModificarReserva);
            var.iOpcioModificar = iLlegirOpcioMenu();

            switch(var.iOpcioModificar){
                case 1:{ //MODIFICAR NOM
                    MD_ModificarNom(var, db, res);

                    break;
                }
                case 2:{ //MODIFICAR COMENSALS
                    MD_ModificarComensals(var, res, db);

                    break;
                }
                case 3:{ //MODIFICAR DIA
                    MD_ModificarDia(var, res, db);

                    break;
                }
                case 4:{ //MODIFICAR MES
                    MD_ModificarMes(var, res, db);

                    break;
                }
                case 5:{ //TORNAR AL MENÚ
                    var.bModificarOps = false;
                    break;
                }
                default :{ //OPCIÓ INVALIDA
                    sOpcioInvalida();
                }
            }
        }
    }
    
    private static void MD_ModificarNom(Variables var,BaseDades db,Reserva res){
        Mostra("Nom actual "+ res.nresserva);

        var.sNomReserva = sLlegirText(var.fNouUsuari);

        Mostra("Nom modificat a " + var.sNomReserva);

        ConnectarDB(db);
        String sQuery = "UPDATE reserves set nom = \'" + var.sNomReserva + "\' where telefon = \'" + res.telefon + "\'";
        UpdateDB(sQuery, db);
        DesconnectarDB(db);
    }
    
    private static void MD_ModificarComensals(Variables var,Reserva res,BaseDades db){
        var.bReserva = true;
        while(true){
            Mostra("Nº actual de comensals: "+res.comensals);
            var.iComensalsR = sLlegirNumero(var.nComensalsReserva);
            ComprovarComensals(var);

            if(var.bReserva == false){
                ConnectarDB(db);
                String sQuery = "UPDATE reserves set comensals = \'" + var.iComensalsR + "\' where telefon = \'" + res.telefon + "\'";
                UpdateDB(sQuery, db);
                DesconnectarDB(db);

                break;
            }
        }
    }
    
    private static void MD_ModificarDia(Variables var,Reserva res,BaseDades db){
        while(true){
            Mostra("Dia seleccionat actualment: "+res.dia);
            var.iDiaReserva = sLlegirNumero(var.nDiaReserva);
            ComprovarDia(var);

            if(var.bReserva == false){
                ConnectarDB(db);
                String sQuery = "UPDATE reserves set dia = \'" + var.iDiaReserva + "\' where telefon = \'" + res.telefon + "\'";
                UpdateDB(sQuery, db);
                DesconnectarDB(db);

                break;
            }
        }
    }
    
    private static void MD_ModificarMes(Variables var,Reserva res,BaseDades db){
        while(true){
            
            Mostra("Mes seleccionat actualment: "+res.mes);
            var.bReserva = true;
            
            while(var.bReserva == true){
                var.iMesReserva = sLlegirNumero(var.nMesReserva);
                ComprovarMes(var);
            }

            if(var.bReserva == false){
                ConnectarDB(db);
                String sQuery = "UPDATE reserves set mes = \'" + var.iMesReserva + "\' where telefon = \'" + res.telefon + "\'";
                UpdateDB(sQuery, db);
                DesconnectarDB(db);

                break;
            }
        }
    }
    
    public static void MD_ModificarReserva(Variables var,Menus Menus,BaseDades db, TReserva reserva,Reserva res){
        var.bModificarRes = true;
        var.bModificarOps = false;

        Menus(Menus.MenuModificarReserva);

        while(var.bModificarRes == true){

            var.iTelefon = sLlegirNumero(var.nTelefonReserva);

            if(var.iTelefon == 13){
                Mostra("\033[33m" + "Modificacio de reserva cancel·lada" + "\033[30m");
                break;
            }

            MD_ComprovarTelefon(var, db, res);

            if(var.bModificarRes == true){
                Mostra("\033[31m" + "Telefon no trobat" + "\033[30m");
            }

        }

        if(var.bModificarOps == true){
            MD_MenuModificar(var, res, db, Menus, reserva);
        }
    }
}
