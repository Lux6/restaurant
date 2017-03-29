package restaurantcopia2;

import java.util.Scanner;

class RestaurantCopia2 extends restaurantFuncionsProcediments {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:restaurant";
    static final String USER = "postgres";
    static final String PASS = "root";

    public static void main (String[]args) {
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
                
                //************************************************************INICI SESSIÓ*******************************************************************//
                case 1:{ //
                    IniciSessio(Menus, var, db, res, reserva, usu);
                    
                    break;
                }
                //**********************************************************REGISTRE USUARI******************************************************************//
                case 2:{
                    RG_RegistreUsuari(var, Menus,db);
                    break;
                }
                
                //*******************************************************RECUPERAR CONTRASENYA*****************************************************************//
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
}