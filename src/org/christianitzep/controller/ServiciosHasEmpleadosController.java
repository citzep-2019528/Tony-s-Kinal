package org.christianitzep.controller;

import com.jfoenix.controls.JFXTimePicker;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.christianitzep.bean.Empleado;
import org.christianitzep.bean.Servicio;
import org.christianitzep.bean.ServiciosHasEmpleados;
import org.christianitzep.db.Conexion;
import org.christianitzep.main.Principal;


public class ServiciosHasEmpleadosController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {GUARDAR, NINGUNO, ACTUALIZAR};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <ServiciosHasEmpleados>listaServiciosHasEmpleados;
    private ObservableList <Empleado>listaEmpleado;
    private ObservableList <Servicio>listaServicio;
    private DatePicker fecha;
    
    @FXML private TextField txtServiciosCodigoServicio;
    @FXML private ComboBox cmbCodigoEmpleado;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private GridPane grpFechaEvento;
    @FXML private JFXTimePicker jfxTimeEvento;
    @FXML private TextField txtLugarEvento;
    @FXML private TableView tblServiciosHasEmpleados;
    @FXML private TableColumn colServiciosCodigoServicio;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colFechaEvento;
    @FXML private TableColumn colHoraEvento;
    @FXML private TableColumn colLugarEvento;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private Button btnEliminar;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private ImageView imgEliminar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fecha = new DatePicker (Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat ("yyyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        fecha.getStylesheets().add("/org/christianitzep/resource/TonysKinal.css");
        fecha.setDisable(true);
        grpFechaEvento.add(fecha, 3, 1);
        cmbCodigoEmpleado.setItems(getEmpleado());
        cmbCodigoServicio.setItems(getServicio());
    }
    public void cargarDatos(){
        tblServiciosHasEmpleados.setItems(getServiciosHasEmpleados());
        colServiciosCodigoServicio.setCellValueFactory(new PropertyValueFactory <ServiciosHasEmpleados, Integer>("Servicios_codigoServicio"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory <ServiciosHasEmpleados, Integer>("codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory <ServiciosHasEmpleados, Integer>("codigoEmpleado"));
        colFechaEvento.setCellValueFactory(new PropertyValueFactory <ServiciosHasEmpleados, Date>("fechaEvento"));
        colHoraEvento.setCellValueFactory(new PropertyValueFactory <ServiciosHasEmpleados, String>("horaEvento"));
        colLugarEvento.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, String>("lugarEvento"));
    }
    
    public void seleccionarElemento(){
        if(tblServiciosHasEmpleados.getSelectionModel().getSelectedItem() != null){
            txtServiciosCodigoServicio.setText(String.valueOf(((ServiciosHasEmpleados)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
            cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServiciosHasEmpleados)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getCodigoServicio()));
            cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((ServiciosHasEmpleados)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
            fecha.selectedDateProperty().set(((ServiciosHasEmpleados)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getFechaEvento());
            jfxTimeEvento.setValue(LocalTime.parse(((ServiciosHasEmpleados)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getHoraEvento()));
            txtLugarEvento.setText(((ServiciosHasEmpleados)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getLugarEvento());
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
        }
    }
    
    public Empleado buscarEmpleado(int codigoEmpleado){
        Empleado resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarEmpleado(?)");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado =  new Empleado (registro.getInt("codigoEmpleado"),
                        registro.getInt("numeroEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("direccionEmpleado"),
                        registro.getString("telefonoContacto"),
                        registro.getString("gradoCocinero"),
                        registro.getInt("codigoTipoEmpleado"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Servicio buscarServicio (int codigoServicio){
        Servicio resultado =  null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarServicio(?)");
            procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Servicio (registro.getInt("codigoServicio"),
                        registro.getDate("fechaServicio"),
                        registro.getString("tipoServicio"),
                        registro.getString("horaServicio"),
                        registro.getString("lugarServicio"),
                        registro.getString("telefonoServicio"),
                        registro.getInt("codigoEmpresa"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList<ServiciosHasEmpleados> getServiciosHasEmpleados(){
        ArrayList<ServiciosHasEmpleados> lista  =  new ArrayList <ServiciosHasEmpleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarServicios_has_Empleados");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ServiciosHasEmpleados(resultado.getInt("Servicios_codigoServicio"),
                        resultado.getInt("codigoEmpleado"),
                        resultado.getInt("codigoServicio"),
                        resultado.getDate("fechaEvento"),
                        resultado.getString("horaEvento"),
                        resultado.getString("lugarEvento")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServiciosHasEmpleados = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Empleado> getEmpleado(){
        ArrayList <Empleado> lista = new ArrayList<Empleado>();
        try{
            PreparedStatement procedimiento =  Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpleados");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleado (resultado.getInt("codigoEmpleado"),
                        resultado.getInt("numeroEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("direccionEmpleado"),
                        resultado.getString("telefonoContacto"),
                        resultado.getString("gradoCocinero"),
                        resultado.getInt("codigoTipoEmpleado")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList <Servicio> getServicio(){
        ArrayList<Servicio> lista = new ArrayList<Servicio>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarServicios");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicio (resultado.getInt("codigoServicio"),
                        resultado.getDate("fechaServicio"),
                        resultado.getString("tipoServicio"),
                        resultado.getString("horaServicio"),
                        resultado.getString("lugarServicio"),
                        resultado.getString("telefonoServicio"),
                        resultado.getInt("codigoEmpresa")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicio =  FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch (tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                imgNuevo.setImage(new Image ("/org/christianitzep/image/Guardar.png"));
                imgEliminar.setImage(new Image("/org/christianitzep/image/Cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                imgNuevo.setImage(new Image("/org/christianitzep/image/Agregar.png"));
                imgEliminar.setImage(new Image("/org/christianitzep/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
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
                imgNuevo.setImage(new Image ("/org/christianitzep/image/Agregar.png"));
                imgEliminar.setImage(new Image("/org/christianitzep/image/Eliminar.png"));
                tipoDeOperacion =  operaciones.NINGUNO;
                break;
            case ACTUALIZAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEditar.setText("Editar");
                imgEditar.setImage(new Image ("/org/christianitzep/image/Editar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                tblServiciosHasEmpleados.getSelectionModel().clearSelection();
                break;
            default:
                if(tblServiciosHasEmpleados.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Servicio Has Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarServicios_has_Empleados(?)");
                            procedimiento.setInt(1, ((ServiciosHasEmpleados)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem()).getServicios_codigoServicio());
                            listaServicio.remove(tblServiciosHasEmpleados.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                            procedimiento.execute();
                        }catch(SQLException e){
                            JOptionPane.showMessageDialog(null, "No se puede borrar un dato con relación");
                            cargarDatos();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else if(respuesta == JOptionPane.NO_OPTION){
                        limpiarControles();
                        desactivarControles();
                        tblServiciosHasEmpleados.getSelectionModel().clearSelection();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        } 
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblServiciosHasEmpleados.getSelectionModel().getSelectedItem() != null){
                    btnNuevo.setDisable(true);
                    btnEliminar.setText("Cancelar");
                    btnEditar.setText("Actualizar");
                    imgEditar.setImage(new Image ("/org/christianitzep/image/Actualizar.png"));
                    imgEliminar.setImage(new Image ("/org/christianitzep/image/Cancelar.png"));
                    activarControles();
                    txtServiciosCodigoServicio.setEditable(false);
                    cmbCodigoServicio.setDisable(true);
                    cmbCodigoEmpleado.setDisable(true);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEditar.setText("Editar");
                imgEditar.setImage(new Image ("/org/christianitzep/image/Editar.png"));
                imgEliminar.setImage(new Image ("/org/christianitzep/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
//    public void reporte(){
//        switch (tipoDeOperacion){
//            case ACTUALIZAR:
//                limpiarControles();
//                desactivarControles();
//                btnNuevo.setDisable(false);
//                btnEditar.setText("Editar");
//                btnReporte.setText("Reporte");
//                imgEditar.setImage(new Image ("/org/christianitzep/image/Editar.png"));
//                imgReporte.setImage(new Image ("/org/christianitzep/image/Reporte.png"));
//                tipoDeOperacion = operaciones.NINGUNO;
//                tblServiciosHasEmpleados.getSelectionModel().clearSelection();
//                break;
//        }
//    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("sp_EditarServicios_has_Empleados(?,?,?,?)");
            ServiciosHasEmpleados registro = (ServiciosHasEmpleados)tblServiciosHasEmpleados.getSelectionModel().getSelectedItem();
            registro.setFechaEvento(fecha.getSelectedDate());
            registro.setHoraEvento(String.valueOf(jfxTimeEvento.getValue()));
            registro.setLugarEvento(txtLugarEvento.getText());
            procedimiento.setInt(1, registro.getServicios_codigoServicio());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setString(3, registro.getHoraEvento());
            procedimiento.setString(4, registro.getLugarEvento());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void guardar(){
        ServiciosHasEmpleados registro = new ServiciosHasEmpleados();
            registro.setServicios_codigoServicio(Integer.parseInt(txtServiciosCodigoServicio.getText()));
            registro.setCodigoServicio(((Servicio)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
            registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            registro.setFechaEvento(fecha.getSelectedDate());
            registro.setHoraEvento(String.valueOf(jfxTimeEvento.getValue()));
            registro.setLugarEvento(txtLugarEvento.getText());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarServicios_has_Empleados(?,?,?,?,?,?)");
                procedimiento.setInt(1, registro.getServicios_codigoServicio());
                procedimiento.setInt(2, registro.getCodigoServicio());
                procedimiento.setInt(3, registro.getCodigoEmpleado());
                procedimiento.setDate(4, new java.sql.Date(registro.getFechaEvento().getTime()));
                procedimiento.setString(5, registro.getHoraEvento());
                procedimiento.setString(6, registro.getLugarEvento());
                procedimiento.execute();
                listaServiciosHasEmpleados.add(registro);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "No se pueden usar los mismos datos");
            }catch(Exception e){
                e.printStackTrace();
        }
    }
    
    
    public void desactivarControles(){
        txtServiciosCodigoServicio.setEditable(false);
        cmbCodigoEmpleado.setDisable(true);
        cmbCodigoServicio.setDisable(true);
        fecha.setDisable(true);
        jfxTimeEvento.setDisable(true);
        txtLugarEvento.setEditable(false);
    }

    public void activarControles(){
        txtServiciosCodigoServicio.setEditable(true);
        cmbCodigoEmpleado.setDisable(false);
        cmbCodigoServicio.setDisable(false);
        fecha.setDisable(false);
        jfxTimeEvento.setDisable(false);
        txtLugarEvento.setEditable(true);
    }
    
    public void limpiarControles(){
        txtServiciosCodigoServicio.clear();
        cmbCodigoEmpleado.setValue(null);
        cmbCodigoServicio.setValue(null);
        fecha.selectedDateProperty().set(null);
        jfxTimeEvento.setValue(null);
        txtLugarEvento.clear();
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
}
