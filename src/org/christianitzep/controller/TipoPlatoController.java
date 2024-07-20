package org.christianitzep.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.christianitzep.bean.TipoPlato;
import org.christianitzep.db.Conexion;
import org.christianitzep.main.Principal;
import org.christianitzep.report.GenerarReporte;

public class TipoPlatoController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR,ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<TipoPlato>listaTipoPlato;
    
    @FXML private TextField txtCodigoTipoPlato;
    @FXML private TextField txtDescripcionTipo;
    @FXML private TableView tblTipoPlatos;
    @FXML private TableColumn colCodigoTipoPlato;
    @FXML private TableColumn colDescripcionTipo;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos(){
        tblTipoPlatos.setItems(getTipoPlato());
        colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<TipoPlato, Integer>("codigoTipoPlato"));
        colDescripcionTipo.setCellValueFactory(new PropertyValueFactory<TipoPlato, String>("descripcionTipo"));
    }
    
    public ObservableList<TipoPlato> getTipoPlato(){
        ArrayList<TipoPlato> lista = new ArrayList<TipoPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareStatement("call sp_ListarTipoPlatos");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new TipoPlato(resultado.getInt("codigoTipoPlato"),
                    resultado.getString("descripcionTipo")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoPlato = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image ("/org/christianitzep/image/Guardar.png"));
                imgEliminar.setImage (new Image ("/org/christianitzep/image/Cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
            break;
            case GUARDAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image ("/org/christianitzep/image/Agregar.png"));
                imgEliminar.setImage(new Image ("/org/christianitzep/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void seleccionarElemento(){
        if(tblTipoPlatos.getSelectionModel().getSelectedItem() != null){
            txtCodigoTipoPlato.setText(String.valueOf(((TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
            txtDescripcionTipo.setText(((TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem()).getDescripcionTipo());
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image ("/org/christianitzep/image/Agregar.png"));
                imgEliminar.setImage(new Image ("/org/christianitzep/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
            default:
              if (tblTipoPlatos.getSelectionModel().getSelectedItem() != null){
                  int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro", "Eliminar Tipo de Plato", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                  if (respuesta == JOptionPane.YES_OPTION){
                      try{
                          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarTipoPlato(?)");
                          procedimiento.setInt(1, ((TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
                          listaTipoPlato.remove(tblTipoPlatos.getSelectionModel().getSelectedIndex());
                          limpiarControles();
                          procedimiento.execute();
                        }catch(SQLException e){
                            JOptionPane.showMessageDialog(null, "No se puede borrar un dato con relación");
                            cargarDatos();
                        }catch(Exception e){
                          e.printStackTrace();
                        }
                    }else if (respuesta  == JOptionPane.NO_OPTION){
                      limpiarControles();
                      desactivarControles();
                      tblTipoPlatos.getSelectionModel().clearSelection();
                    }
                }else{
                  JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
              }
        }
    }
    
    public void editar(){
        switch (tipoDeOperacion){
            case NINGUNO:
                if(tblTipoPlatos.getSelectionModel().getSelectedItem() != null){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image ("/org/christianitzep/image/Actualizar.png"));
                    imgReporte.setImage(new Image ("/org/christianitzep/image/Cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
            break;
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/christianitzep/image/Editar.png"));
                imgReporte.setImage(new Image("/org/christianitzep/image/Reporte.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/christianitzep/image/Editar.png"));
                imgReporte.setImage(new Image ("/org/christianitzep/image/Reporte.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                tblTipoPlatos.getSelectionModel().clearSelection();
                break;
            default:
                imprimirReporte();
        }
    }
    
    public void imprimirReporte(){
         Map parametros = new HashMap();
         URL ruta = this.getClass().getResource("/org/christianitzep/image/Fondo2.png");
         parametros.put("imagen", ruta);
         parametros.put("codigoTipoPlato", null);
         GenerarReporte.mostrarReporte("ReporteTipoPlato.jasper", "Reporte de Tipo Plato", parametros);
         
     }
    
    public void actualizar(){
         if(txtDescripcionTipo.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Faltan Datos");
        }else{
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarTipoPlato (?, ?)");
                TipoPlato registro = (TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem();
                registro.setDescripcionTipo(txtDescripcionTipo.getText());
                procedimiento.setInt(1, registro.getCodigoTipoPlato());
                procedimiento.setString(2, registro.getDescripcionTipo());
                procedimiento.execute();
            }catch(Exception e){
                e.printStackTrace();
            }
        }    
    }
    
    public void guardar(){
        TipoPlato registro = new TipoPlato();
        if(txtDescripcionTipo.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Faltan Datos");
        }else{
            registro.setDescripcionTipo(txtDescripcionTipo.getText());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarTipoPlato (?)");
                procedimiento.setString(1, registro.getDescripcionTipo());
                procedimiento.execute();
                listaTipoPlato.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void desactivarControles(){
        txtCodigoTipoPlato.setEditable(false);
        txtDescripcionTipo.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoTipoPlato.setEditable(false);
        txtDescripcionTipo.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoTipoPlato.clear();
        txtDescripcionTipo.clear();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaPlato(){
        escenarioPrincipal.ventanaPlato();
    }
}
