package restaurantcopia2;

import java.util.Scanner;
import static restaurantcopia2.Menus.*;
import static restaurantcopia2.RestaurantBaseDadesMD.*;

public class RestaurantProcedimentsBasics extends Menus{
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
     * @param reserva
     * @param comp 
     */
    public static void ComprovarDia(TReserva reserva,bComprobadors comp){
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
    * @param reserva
    * @param comp 
    */
    public static void ComprovarComensals(TReserva reserva,bComprobadors comp){
            if(reserva.iComensalsR <= 11 && reserva.iComensalsR > 0){
                System.out.println("Ha reservat taula per a "+ reserva.iComensalsR);
                comp.bReserva = false;
            }else{ 
                System.out.println("\033[31m" + "*Comensals erronis*" + "\033[30m");
                comp.bReserva = true;
            }
        }
    
    /**
     * Comproba si el mes introduit esta entre els valors valids (1-12)
     * @param var
     * @param reserva
     * @param Menus
     * @param comp 
     */
    public static void ComprovarMes(TReserva reserva, Menus Menus,bComprobadors comp){
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
    
    
}
