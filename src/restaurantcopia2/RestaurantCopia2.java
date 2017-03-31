package restaurantcopia2;

import java.util.Scanner;

class RestaurantCopia2 extends RestaurantFuncionsProcediments {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:restaurant";
    static final String USER = "postgres";
    static final String PASS = "root";

    public static void main (String[]args) {
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
        
        int iOpcioPrincipal;    //Opcio menú
        boolean bRestaurant = true;
    
        while(bRestaurant == true){
            //Mostra el menu principal i llegeix opció del menu
            Menus(Menus.MenuPrincipal);
            iOpcioPrincipal = iLlegirOpcioMenu();
            
            //Comprova si l'usuari vol tancar el programa.
            if(iOpcioPrincipal == 3){
                Mostra(missatge.fComiat);
                
                bRestaurant = false;
            }
            
            if(bRestaurant == true){
                switch(iOpcioPrincipal){

                    //************************************************************INICI SESSIÓ*******************************************************************//
                    case 1:{ //
                        IniciSessio(Menus, ops, db, dbres, reserva, dbusu, missatge, comp, tusu);

                        break;
                    }
                    //**********************************************************REGISTRE USUARI******************************************************************//
                    case 2:{
                        RG_RegistreUsuari(ops, Menus, db, missatge, comp, tusu, dbusu);
                        break;
                    }

                    //Opcio del menú incorrecte
                    default:{
                        sOpcioInvalida();
                    }
                }
            }
        }
    }
}