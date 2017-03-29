package restaurantcopia2;

import com.sun.org.apache.xml.internal.resolver.readers.TR9401CatalogReader;
import java.util.Scanner;
import java.sql.*;
import static restaurantcopia2.restaurantFuncionsProcediments.*;

public class RestaurantCopia2 extends restaurantFuncionsProcediments {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:restaurant";
    static final String USER = "postgres";
    static final String PASS = "root";

    public final void main(Menus Menus,Variables var,BaseDades db,Reserva res,TReserva reserva,Usuari usu) {
                
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
}