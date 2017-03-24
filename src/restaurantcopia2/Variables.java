/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantcopia2;

/**
 *
 * @author jerje
 */
public class Variables {
        int iOpcioPrincipal;    //Opcio menú
        int iOpcioReserva;      //Opcio reserva
        int iOpcioSolicitud;    //Opcio Sol·licitud Reserva
        int iOpcioModificar;    //Opcio Modificar Reserva
        int iUsuari = 0;        //Posicio usuari admin/usu
        int iGestio = 0;        //Opcions administrador
        int iMes = 0;           //Mes per cercar reserves administrador
        int iNumeroReserva = 0; //Numero de reserva 
        int c = 0;              //Posicio Base de dades reserva
        int iTelefon;           //Variable numero telefon
        int iMesReserva = 0;    //Variable Mes reserva
        int iDiaReserva = 0;    //Variable dia reserva
        int iComensalsR =0;     //Variable comensals reserva
        
        String nTelefonReserva = "Introdueixi el numero de telefon amb el que va fer la reserva:\n -> ";    //Text demanar Telefon reserva(Visualitzat/Modificar)
        String nTelReserva = "Introduexi el numero de telefon:\n -> ";                                      //Text demanar Telefon reserva (Sol·licitar)
        String fNomReserva = "Introdueixi el nom al que vol fer la reserva:\n -> ";                         //Text demanar nom reserva
        String sNomReserva;                                                                                 //Variable nom reserva
        String nComensalsReserva = "Introduexi el numero de comensals(1-11):\n -> ";                        //Text demanr comensals reserva
        String sConfirmar;                                                                                  //Variables confiramar reserva
        String sUsuari;                                                                                     //Variable usuari
        String sContrasenya;                                                                                //Variable contrasenya
        String nDiaReserva = "Introduexi ara el dia desitjat(1-29):\n -> ";                                 //Text demanar dia reserva
        String nMesReserva = "Numero del mes de la reserva:\n -> ";
        String sContrasenyaRep;                                                                             //Variable contrasenya repetida
        String sCorreu;                                                                                     //Variable correu//Text demanar Mes reserva
        String fNovaContrasenya = "Nova Contrasenya:\n -> ";                                                //Text demanr nova contrasenya (Registrar-se/Recuperar)
        String fRepContrasenya = "Repeteix Contrasenya:\n -> ";                                             //Text demanar repetir contrsenya (Registrar usuari)
        String fUsuari = "Nom usuari:\n -> ";                                                               //Text demanar nom d'usuari
        String fContrasenya = "Contrasenya:\n -> ";                                                         //Text demanar contrasenya
        String fNouUsuari = "Nou nom d'Usuari:\n -> ";                                                      //Text demanr nou nom d'usuari
        String fCorreu = "Introdueix el vostre correu\n -> ";                                               //Text demanar correu (Registrar usuari)
        String fComiat = "\033[35m" + "Adeu fins la proxima!" + "\033[30m";
        String sReservaNoTrobada="\033[31m" + "Reserva no trobada." + "\033[30m";
        
        boolean bTelefon = false;   //Telefon unic
        boolean bUsuariC;           //Comprovar si l'usuari es 
        boolean bReserva = true;    //Comprovar si es continua amb la reserva
        boolean bRecuperacio;       //Bucle Recuperacio Contrasenya
        boolean bComprovacio;       //Usuri i correu coincideixen (?)
        boolean bBuscar = true;     //Reserva trobada o no (Buscar Reserva)
        boolean bRegistre = true;   //Bucle Registrar usuari
        boolean bRegistreC;         //Comprovar si el registre s'ha fet correctament
        boolean bOP = true;         //Bucle Menú Principal
        boolean bIniciar = true;    //Bucle Iniciar Sessio       
        boolean bAdmin = true;      //Bucle Menú administrador
        boolean bModificarRes;      //Bucle Menú Modificar Reserva    
        
        String[][] sTUsuari = new String[99][4];//Base de dades usuari    
        String sMeses[] = {"Gener","Febrer","Març","Abril","Maig","Juny","Juliol","Agost","Septembre","Octubre","Novembre","Desembre"};

}
