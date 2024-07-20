/*
    Christian Emanuel Itzep Lemus
    Carné: 
        2019528
    Código Técnico: 
        IN5AM
    Fecha de Creación: 
        28/03/2023
    Fechas de Modificación:
        11/04/2023
        12/04/2023
        15/04/2023
        16/04/2023
        17/04/2023
        18/04/2023
        19/04/2023
        21/04/2023
        22/04/2023
        23/04/2023
        26/04/2023
        02/05/2023
        03/05/2023
        05/05/2023
        06/05/2023
        09/05/2023
        10/05/2023
        23/05/2023
        26/05/2023
        27/05/2023
        28/05/2023
        29/05/2023
        30/05/2023
        31/05/2023
        01/06/2023
        02/06/2023
        05/06/2023
        06/06/2023
*/
package org.christianitzep.main;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.christianitzep.controller.CartaMenuController;
import org.christianitzep.controller.EmpleadoController;
import org.christianitzep.controller.EmpresaController;
import org.christianitzep.controller.LoginController;
import org.christianitzep.controller.MenuPrincipalController;
import org.christianitzep.controller.PlatoController;

import org.christianitzep.controller.PresupuestoController;
import org.christianitzep.controller.ProductoController;
import org.christianitzep.controller.ProductosHasPlatosController;
import org.christianitzep.controller.ProgramadorController;
import org.christianitzep.controller.ServicioController;
import org.christianitzep.controller.ServiciosHasEmpleadosController;
import org.christianitzep.controller.ServiciosHasPlatosController;
import org.christianitzep.controller.TipoEmpleadoController;
import org.christianitzep.controller.TipoPlatoController;
import org.christianitzep.controller.UsuarioController;

public class Principal extends Application {

    private final String PAQUETE_VISTA = "/org/christianitzep/view/";
    private Stage escenarioPrincipal;
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal 2023");
        escenarioPrincipal.getIcons().add(new Image("/org/christianitzep/image/Logo2.png"));
//        Parent root = FXMLLoader.load(getClass().getResource("/org/christianitzep/view/MenuPrincipalView.fxml"));
//        Scene escena = new Scene(root);    
//        escenarioPrincipal.setScene (escena);
//        menuPrincipal();
        ventanaLogin();
        escenarioPrincipal.show();

    }

    public void menuPrincipal() {
        try {
            MenuPrincipalController menu = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 501, 441);
            menu.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void cartaMenu(){
        try{
            CartaMenuController menu = (CartaMenuController) cambiarEscena("CartaMenuView.fxml", 420, 597);
            menu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ventanaProgramador(){
        try{
            ProgramadorController programador = (ProgramadorController) cambiarEscena ("ProgramadorView.fxml",424,469);
            programador.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpresa(){
        try{
            EmpresaController empresa =  (EmpresaController) cambiarEscena ("EmpresaView.fxml", 701, 492);
            empresa.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProducto(){
        try{
            ProductoController producto = (ProductoController) cambiarEscena ("ProductoView.fxml",701, 492);
            producto.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoPlato(){
        try{
            TipoPlatoController tipoPlato = (TipoPlatoController) cambiarEscena ("TipoPlatoView.fxml", 701, 492);
            tipoPlato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoEmpleado(){
        try{
            TipoEmpleadoController tipoEmpleado = (TipoEmpleadoController) cambiarEscena ("TipoEmpleadoView.fxml", 701,492);
            tipoEmpleado.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPresupuesto(){
        try{
            PresupuestoController presupuesto = (PresupuestoController) cambiarEscena ("PresupuestoView.fxml", 701, 492);
            presupuesto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   
    public void ventanaPlato(){
        try{
            PlatoController plato = (PlatoController) cambiarEscena ("PlatoView.fxml", 701, 492);
            plato.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleado(){
        try{
            EmpleadoController empleado = (EmpleadoController) cambiarEscena ("EmpleadoView.fxml", 903, 492);
            empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServicio(){
        try{
            ServicioController servicio = (ServicioController) cambiarEscena ("ServicioView.fxml", 701, 492);
            servicio.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProductosHasPlatos(){
        try{
            ProductosHasPlatosController has =  (ProductosHasPlatosController) cambiarEscena ("Productos_has_PlatosView.fxml", 701, 492);
            has.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServiciosHasPlatos(){
        try{
            ServiciosHasPlatosController has = (ServiciosHasPlatosController) cambiarEscena ("Servicios_has_PlatosView.fxml", 701, 492);
            has.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServiciosHasEmpleados(){
        try{
            ServiciosHasEmpleadosController has  = (ServiciosHasEmpleadosController) cambiarEscena("Servicios_has_EmpleadosView.fxml", 701, 492);
            has.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLogin(){
        try{
            LoginController login = (LoginController) cambiarEscena ("LoginView.fxml", 649, 400);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuario(){
        try{
           UsuarioController usuario = (UsuarioController) cambiarEscena ("UsuarioView.fxml",701,492);
           usuario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA + fxml));
        escena = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.setResizable(false);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();
        return resultado;
    }
}
