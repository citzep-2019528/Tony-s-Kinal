package org.christianitzep.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.christianitzep.bean.Usuario;
import org.christianitzep.db.Conexion;
import org.christianitzep.main.Principal;


public class UsuarioController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{GUARDAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    
    @FXML private TextField txtCodigoUsuario;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtApellidoUsuario;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtContrasena;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                imgNuevo.setImage(new Image("/org/christianitzep/image/Guardar.png"));
                imgEliminar.setImage(new Image("/org/christianitzep/image/Cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                limpiarControles();
                activarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image ("/org/christianitzep/image/Agregar.png"));
                imgEliminar.setImage((new Image ("/org/christianitzep/image/Eliminar.png")));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void cancelar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/christianitzep/image/Agregar.png"));
                imgEliminar.setImage(new Image("/org/christianitzep/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void guardar(){
        Usuario registro = new Usuario();
        if(txtNombreUsuario.getText().isEmpty()||txtApellidoUsuario.getText().isEmpty()||txtUsuario.getText().isEmpty()||txtContrasena.getText().isEmpty()){
           JOptionPane.showMessageDialog(null, "Faltan datos");
        }else{
         registro.setNombreUsuario(txtNombreUsuario.getText());
         registro.setApellidoUsuario(txtApellidoUsuario.getText());
         registro.setUsuarioLogin(txtUsuario.getText());
         registro.setContrasena(txtContrasena.getText());
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarUsuario(?,?,?,?)");
             procedimiento.setString(1, registro.getNombreUsuario());
             procedimiento.setString(2, registro.getApellidoUsuario());
             procedimiento.setString(3, registro.getUsuarioLogin());
             procedimiento.setString(4, registro.getContrasena());
             procedimiento.execute();
             JOptionPane.showMessageDialog(null, "Usuario registrado");
             ventanaLogin();
         }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Este usuario ya existe\n Utilice otro");
         }catch(Exception e){
             e.printStackTrace();
         }
         
        }
    }

    public void desactivarControles(){
        txtCodigoUsuario.setEditable(false);
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuario.setEditable(false);
        txtContrasena.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoUsuario.setEditable(false);
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuario.setEditable(true);
        txtContrasena.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoUsuario.clear();
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuario.clear();
        txtContrasena.clear();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
    
}
