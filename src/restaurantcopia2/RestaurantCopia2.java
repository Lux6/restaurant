package restaurantcopia2;

import java.util.Scanner;
import java.sql.*;

public class RestaurantCopia2 {
    /**
     *
     */
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:restaurant";
    static final String USER = "postgres";
    static final String PASS = "root";
    
    public static void main(String[] args) {
        //*Reservar espacio de clases*//
        Scanner sc = new Scanner(System.in);
        TReserva reserva = new TReserva();
        Menus Menus = new Menus();
        Variables var = new Variables();
        BaseDades db = new BaseDades();
        Usuari usu = new Usuari();
        Reserva res = new Reserva();
                
        while(true){
            
            //Mostra el menu principal i llegeix opció del menu
            Menus(Menus.MenuPrincipal);
            var.iOpcioPrincipal = iLlegirOpcioMenu();
            
            //Comprova si l'usuari vol tancar el programa.
            if(var.iOpcioPrincipal == 4){
                Mostra(var.fComiat);
                break;
            }
            
            switch(var.iOpcioPrincipal){
                
                //************************************************************INICI SESSIÓ*****************************************************************//
                case 1:{ //
                    IniciSessio(Menus, var, db, res, reserva, usu);
                    
                    break;
                }
                //*************************************************************REGISTRE USUARI**********************************************************//
                case 2:{
                    RG_RegistreUsuari(var, Menus,db);
                    break;
                }
                
                //*************************************************************RECUPERAR CONTRASENYA*************************************************//
                case 3:{
                    RecuperarContrasenya(var, Menus);
                    break;
                }
                
                //Opcio del menú incorrecte
                default:{
                    sOpcioInvalida();
                }
            }
        }
    }
    /**Registre un usuari nou a la base de dades 
     * 
     * @param var
     * @param Menus
     * @param db 
     */
    public static void RG_RegistreUsuari(Variables var,Menus Menus,BaseDades db){
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
    public static void ComprovarDia(Variables var){
        if(var.iDiaReserva <= 29 && var.iDiaReserva > 0){
            System.out.println("Ha escollit el dia "+var.iDiaReserva);
            var.bReserva = false;
        }else{
            System.out.println("\033[31m" + "*Dia erroni*" + "\033[30m");
            var.bReserva = true;
        }
   
    }
    
    /**
     * Comproba si els comensals introduits estan entre els valors valids (1-10)
     * @param var
     * @param iComensalsR
     * @return 
     */
    public static void ComprovarComensals(Variables var){
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
     * @param var
     * @param nTelReserva
     * @param bTelefon
     * @param reserva
     * @return 
     */
    public static void ComprovarTelefon(Variables var,TReserva reserva){
        while(true){
            var.bTelefon = true;
            var.iTelefon = sLlegirNumero(var.nTelReserva);
            
            //Comprova que el telefon estigui dintre del rang valid
            if(var.iTelefon < 1000000000 && var.iTelefon > 600000000){
                for(int i = 0;i < 1000;i++){
                    
                    if(var.iTelefon == reserva.iTelefon){
                        var.bTelefon = false;
                    }
                }
                if(var.bTelefon == true){
                    System.out.println("El telefon de contacte i el numero amb el qual podra modificar la reserva és "+ var.iTelefon);
                    
                    break;
                }else{
                    System.out.println("\033[31m" + "*Numero ocupat*" + "\033[01m");
                }        
            }else{
                System.out.println("\033[31m" + "*Format de telefon erroni*" + "\033[30m");
            }
        }
    }
    
    /**
     * Comproba si el mes introduit esta entre els valors valids (1-12)
     * @param var
     * @param iMesReserva
     * @param reserva
     * @param nMesReserva
     * @param sMeses
     * @return 
     */
    public static void ComprovarMes(Variables var){
        var.bCancelarRes = false;

        if(var.iMesReserva == 13){//Comprova si es 13 per cancel·lar
            var.bReserva = false;

        }else if(var.iMesReserva <= 12 && var.iMesReserva >= 0){
            System.out.println("Ha escollit "+var.sMeses[var.iMesReserva-1]);
            var.bReserva = false;

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
    public static void MostrarReserva(Variables var,BaseDades db,Reserva res){
        try {
            String sQuery = "SELECT * FROM reserves where telefon= "+var.iTelefon+";";
            ConnectarDB(db);
            QueryDB(sQuery, db);

            while (db.rs.next()) {
                res.dia = db.rs.getInt("dia");
                res.mes = db.rs.getInt("mes");
                res.comensals = db.rs.getInt("comensals");
                res.nresserva = db.rs.getString("nom");
                res.telefon = db.rs.getInt("telefon");
                if(var.iTelefon == res.telefon){   
                    System.out.println("---------------------\n DADES DE LA RESERVA \n---------------------\n"
                        + "Nom: "+res.nresserva+"\n"
                        + "Nº Comensals: "+res.comensals+"\n"
                        + "Dia: "+res.dia+"\n"
                        + "Mes: "+var.sMeses[res.mes]+"\n"
                        + "Telefon y numero de registre: "+res.telefon);

                    var.bBuscar = false;
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
    public static void BuscarReserva (Variables var,Menus Menus,BaseDades db,Reserva res){
        var.bBuscar = true;  
        
        //Mostra el menu de Buscar Reserva
        Menus(Menus.MenuBuscarReserva);
        
        //Llegeix les dades per buscar reserva
        var.iTelefon = sLlegirNumero(var.nTelefonReserva);

        //Mostra la Reserva
        MostrarReserva(var, db, res);
        //Comprueba si la reserva se ha mostrado, sino muestra mensaje de no encontrada
        if(var.bBuscar == true){
            Mostra(var.sReservaNoTrobada);
        }
    }
    
    public static void EsborrarReserva (Variables var,BaseDades db){
        ConnectarDB(db);
        String sQuery = ("DELETE FROM reserves where telefon="+var.iTelefon+";");
        UpdateDB(sQuery, db);
        Mostra("\033[32mReserva cancel·lada correctament\033[30m");
        DesconnectarDB(db);
    }
    
    public static void CancelarReserva (Variables var,Menus Menus,BaseDades db){
       //Mostra el menu de Cancelar Reserva
        Menus(Menus.MenuCancelarReserva);
        //Llegeix les dades per cancelar reserva
        var.iTelefon = sLlegirNumero(var.nTelefonReserva);

        EsborrarReserva(var, db);
    }
    
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
    
    /**
     * Sol·licitar Reserva
     * @param Menus
     * @param reserva
     * @param sMeses
     * @param var 
     */
    public static void IntroduccionComprobacionDatos(Menus Menus,Variables var,BaseDades db,Reserva res){
        var.bCancelarRes = false;
        
        while(true){
            //*Mostra el menu de solicitar reserva*//
            Menus(Menus.MenuSolicitarReserva);

            //Mes de la reserva
            var.bReserva = true;
            while(var.bReserva == true){
                var.iMesReserva = sLlegirNumero(var.nMesReserva);
                ComprovarMes(var);
            }

            //Comprova si el mes es valid
            if(var.iMesReserva == 13){
                System.out.println("\033[33m" + "Sol·licitud de reserva cancel·lada" + "\033[30m");
                break;
            }

            //Dia de la reserva
            var.bReserva = true;
            while(var.bReserva == true){
                var.iDiaReserva = sLlegirNumero(var.nDiaReserva);
                ComprovarDia(var);
            }

            //Numero de comensals
            var.bReserva = true;
            while(var.bReserva == true){
                var.iComensalsR = sLlegirNumero(var.nComensalsReserva);
                ComprovarComensals(var);
            }

            //Nom de la reserva
            var.sNomReserva = sLlegirText(var.fNomReserva);

            //Numero de telefon
            var.bReserva = true;
            while(var.bReserva == true){
                var.iTelefon = sLlegirNumero(var.nTelReserva);
                MD_ComprovarTelefon(var, db, res);
                
                if(var.bTelefon == true){
                    break;
                }else{
                    Mostra("\033[31m" + "Telefon ja existent o invalid!" + "\033[30m");
                }
            }
            
            // MostarDesarDades(var, db);
            MostarDesarDades(var, db);
            
            break;
        }
        
        
        
    } 
    
    /**
     * Sol·licitr Reseva
     * @param var
     * @param db
     * @param sMeses 
     */
    public static void MostarDesarDades(Variables var,BaseDades db){
        Scanner sc = new Scanner(System.in);
        
        /**Mostrar Dades**/
        System.out.println("---------------\nRESUM DE LA RESERVA \n---------------\nNom: "+ var.sNomReserva +"\n"
            + "Nº Comensals: "+var.iComensalsR+"\n"
            + "Dia: "+var.iDiaReserva+"\n"
            + "Mes: "+var.sMeses[var.iMesReserva-1]+"\n"
            + "Telefon y numero de registre: "+var.iTelefon);

        System.out.print("Confirmar (s): ");
        var.sConfirmar = sc.next();
        
        /**Guardar Dades**/
        if(var.sConfirmar.equals("s")){
            ConnectarDB(db);
            String sQuery = ("INSERT INTO reserves (dia,mes,comensals,telefon,nom) "
                + "VALUES ("+ var.iDiaReserva +','+ var.iMesReserva 
                +','+ var.iComensalsR +','+ var.iTelefon +','+ '\''+var.sNomReserva+ '\'' +")");
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
    public static void ComprovarUsuari(Variables var,BaseDades db,Usuari usu){
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
    
    public static void SolicitarReserva(Variables var,BaseDades db,Menus Menus,Reserva res){
        while(true){
            //Introduccio i comprovacio de Dades    
            IntroduccionComprobacionDatos(Menus, var, db, res);

            //Resum i guardar les dades
            MostarDesarDades(var, db);

            break;
        }
    }
    
    /**
     * Recuperar contrasenya
     * @param var
     * @param sTUsuari 
     */
    public static void ComprovarCorreuUsuari(Variables var){
        for(int i = 0;i<var.sTUsuari.length;i++){
            
            if(var.sUsuari.equals(var.sTUsuari[i][0])){
                
                if(var.sCorreu.equals(var.sTUsuari[i][2])){
                    
                    var.bComprovacio = true;//Usuari i correu coincideixen

                    var.sContrasenya = sLlegirText(var.fNovaContrasenya);
                    var.sContrasenyaRep = sLlegirText(var.fRepContrasenya);

                    //Comprova les contrasenyes coincideixen
                    if(var.sContrasenya.equals(var.sContrasenyaRep)){
                        var.sTUsuari[i][1] = var.sContrasenya;
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
    public static void RecuperarContrasenya(Variables var,Menus Menus){
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
            ComprovarCorreuUsuari(var);

            //Si l'usuari i correu no conicideixen
            if(bComprovacio == false){
                System.out.println("\033[31m" + "Usuari o correu incorrecte" + "\033[30m");
            }
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
    
    private static void MD_ComprovarTelefon(Variables var,BaseDades db,Reserva res){
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
    
    public static void MostarTotesReserves(Variables var,BaseDades db,Reserva res){
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
                res.nresserva = db.rs.getString("nom");
                res.telefon = db.rs.getInt("telefon");

                Mostra(" > RESERVA Nº"+ var.iNumeroReserva +" \n---------------\n"
                    + "Nom: "+res.nresserva+"\n"
                    + "Nº Comensals: "+res.comensals+"\n"
                    + "Dia: "+res.dia+"\n"
                    + "Mes: "+var.sMeses[res.mes-1]+"\n"
                    + "Telefon y numero de registre: "+res.telefon +"\n");

                var.iNumeroReserva++;
            }
        }  catch (Exception e) {
        }
        DesconnectarDB(db);
    }
    
    public static void MenuClient(Menus Menus,Variables var,BaseDades db,Reserva res,TReserva reserva){
        while(var.iOpcioSolicitud != 5){    

            //Menu principal
            Menus(Menus.MenuReserves);
            var.iOpcioSolicitud = iLlegirOpcioMenu();

            switch(var.iOpcioSolicitud){
                case 1:{ //*************************************SOLICITAR RESERVA*****************************************************/
                    SolicitarReserva(var, db, Menus, res);
                    break;
                } 

                case 2:{ //************************************CANCELAR RESERVA******************************************************//
                    CancelarReserva(var, Menus, db);
                    break;
                }

                case 3:{ //*************************************BUSCAR RESERVA******************************************************//
                    BuscarReserva(var, Menus, db, res);
                    break;
                }

                case 4:{ //************************************MODIFICAR RESERVA****************************************************//
                    MD_ModificarReserva(var, Menus, db, reserva, res);

                    break;
                }    
                case 5:{ //TANCA SESSIÓ
                    Mostra(var.fComiat);
                    break;
                }

                default:{ //OPCIÓ INVALIDA
                    sOpcioInvalida();
                }
            }
        }
    }
    
    public static void MostrarReservesPerMes(Menus Menus,Variables var,Reserva res,BaseDades db){
        while(true){
            Menus(Menus.MenuReservaPerMes);
            var.iMes = sLlegirNumero(var.nMesReserva);

            if(var.iMes == 13){//Comprova si l'usuari vol cancel·lar la cerca
                Mostra("\033[33m" + "Busqueda reserva per mes cancel·lada" + "\033[30m");

                break;
            }else if(var.iMes > 0 && var.iMes < 13){
                Mostra("-----------\n "+ var.sMeses[var.iMes-1]+ " \n------------\n");

                var.iNumeroReserva = 1;
                try {
                    String sQuery = "SELECT * FROM reserves where mes= "+var.iMes +";";
                    ConnectarDB(db);
                    QueryDB(sQuery, db);

                    while (db.rs.next()) {
                        res.dia = db.rs.getInt("dia");
                        res.mes = db.rs.getInt("mes");
                        res.comensals = db.rs.getInt("comensals");
                        res.nresserva = db.rs.getString("nom");
                        res.telefon = db.rs.getInt("telefon");

                        Mostra(" > RESERVA Nº"+ var.iNumeroReserva +" \n---------------\n"
                            + "Nom: "+res.nresserva+"\n"
                            + "Nº Comensals: "+res.comensals+"\n"
                            + "Dia: "+res.dia+"\n"
                            + "Mes: "+var.sMeses[res.mes-1]+"\n"
                            + "Telefon y numero de registre: "+res.telefon +"\n");

                        var.iNumeroReserva++;
                    }
                }  catch (Exception e) {
                }
                DesconnectarDB(db);

                break;

            }else{
                sOpcioInvalida();
            }
        }
    }
    
    public static void MenuAdministrador(Menus Menus,Variables var,Reserva res,BaseDades db){
        var.bAdmin = true;

        while(var.bAdmin == true){

            Menus(Menus.MenuGestioReserves);
            var.iGestio = iLlegirOpcioMenu();

            //*****************************************************RESERVES PER MES************************************************//
            switch (var.iGestio){
                case 1:{ //RESERVA PER MES
                    MostrarReservesPerMes(Menus, var, res, db);

                    break;
                }
                //*****************************************************TOTES LES RESERVA*******************************************//
                case 2:{ //MOSTRAR TOTES LES RESERVES
                    MostarTotesReserves(var, db, res);

                    break;
                }

                case 3:{ //TANCAR SESSIÓ
                    Mostra("\033[35m" + "Adeu fins un altre!" + "\033[30m");
                    var.bAdmin = false;

                    break;
                }

                default:{ //OPCIÓ INVALIDA
                    sOpcioInvalida();
                }
            }
        }
    }
    
    public static void IniciSessio(Menus Menus,Variables var,BaseDades db,Reserva res,TReserva reserva,Usuari usu){
        while(var.bIniciar == true){
            Mostra("----------------\n INICIAR SESSIÓ \n----------------");

            Mostra("Escriu 'adeu' per tornar al menu");
            //Demanar i guardar nom d'usuari
            var.sUsuari = sLlegirText(var.fUsuari);
            //Comprovar si l'usuari vol cancel·lar l'inici de sessio

            if(var.sUsuari.equals("adeu")){
                Mostra("\033[33m" + "Inici de sessió cancel·lat" + "\033[30m");
                break;
            }
            //Demanar i guardar contrasenya
            var.sContrasenya = sLlegirText(var.fContrasenya);

            //Comprovar usuari
            ComprovarUsuari(var, db, usu);

            //Mostra missatge depenent si l'usuari s'ha connectat correctament o no
            if(var.bUsuariC == false){
                Mostra("\033[47m" + "\033[31m" + "Usuari o contrasenya incorrecte" + "\033[30m");
            }else{
                Mostra("\033[47m" + "\033[32m" + "S'ha iniciat la sessió correctament" + "\033[30m");
                //*****************************************************MENU CLIENT************************************************//                         
                if(usu.tipus.equals("usu")){
                    MenuClient(Menus, var, db, res, reserva);

                //*****************************************************MENU ADMINISTRADOR************************************************//
                }else if(usu.tipus.equals("admin")){

                }
            }
        }
    }
}