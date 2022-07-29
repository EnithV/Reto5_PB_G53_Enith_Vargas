package Vistas;

import Modelo.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Controlador.*;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
/*
 * @author Enith Vargas PB G53
 */
public class UserMenu extends javax.swing.JFrame {
    //1. Instancia de la clase Conexión;
    Conexion conexion = new Conexion ();
    Connection connection;
    //2. La librería Statement permite ejecutar los query SQL
    Statement st;
    ResultSet rs;
    //3. Creamos una instancia de la tabla de la interfaz
    DefaultTableModel contenidoTablaEmpleados;
    DefaultTableModel contenidoTablaDepartamentos;
    ComboBoxModel modelEnumDepartamentos;
    ComboBoxModel modelEnumZona;
    ComboBoxModel modelEnumTipoCalle;
    
    public UserMenu() {
        modelEnumDepartamentos = new DefaultComboBoxModel(EnumDepartamento.values());
	modelEnumZona = new DefaultComboBoxModel(EnumZona.values());
	modelEnumTipoCalle = new DefaultComboBoxModel(EnumTipoCalle.values());
        initComponents();
        setLocationRelativeTo(null);
        listarEmpleados();
        listarDepartamentos();
    }
   
    //4. Método que trae todos los empleados existentes en la base de datos
    private void listarEmpleados(){
        String filtroBusqueda = txtSearch.getText();
        if (filtroBusqueda.isEmpty()) {
            String queryConsulta = "SELECT nombreEmp,apellidos,tipoDocumento,documento,correo, nombreSucursal FROM empleado INNER JOIN sucursal ON empleado.FK_idSucursal = sucursal.idSucursal";
            try {
                connection = conexion.getConnection();
                st = connection.createStatement();
                rs = st.executeQuery(queryConsulta);
                Object[] empleados = new Object[6];
                contenidoTablaEmpleados = (DefaultTableModel) tblEmpleados.getModel();
                while (rs.next()) {
                    empleados[0] = rs.getString("nombreEmp");
                    empleados[1] = rs.getString("apellidos");
                    empleados[2] = rs.getString("tipoDocumento");
                    empleados[3] = rs.getString("documento");
                    empleados[4] = rs.getString("correo");
                    empleados[5] = rs.getString("nombreSucursal");
                    contenidoTablaEmpleados.addRow(empleados);
                    tblEmpleados.setModel(contenidoTablaEmpleados);
                }
            } catch (SQLException e) {
                System.out.println("Error");
            }
        } else {
            String queryConsulta = "SELECT nombreEmp,apellidos,tipoDocumento,documento,correo, nombreSucursal FROM empleado INNER JOIN sucursal ON empleado.FK_idSucursal = sucursal.idSucursal AND nombreEmp LIKE '%" + filtroBusqueda + "%' OR apellidos LIKE '%" + filtroBusqueda + "%'";
            System.out.println(queryConsulta);
            try {
                connection = conexion.getConnection();
                st = connection.createStatement();
                rs = st.executeQuery(queryConsulta);
                Object[] empleados = new Object[6];
                contenidoTablaEmpleados = (DefaultTableModel) tblEmpleados.getModel();
                while (rs.next()) {
                    empleados[0] = rs.getString("nombreEmp");
                    empleados[1] = rs.getString("apellidos");
                    empleados[2] = rs.getString("tipoDocumento");
                    empleados[3] = rs.getString("documento");
                    empleados[4] = rs.getString("correo");
                    empleados[5] = rs.getString("nombreSucursal");
                    contenidoTablaEmpleados.addRow(empleados);
                    tblEmpleados.setModel(contenidoTablaEmpleados);
                }
            } catch (SQLException e) {
                System.out.println("Error");
            }
        }
      
    }
    
    // Cuando añado un nuevo empleado el proceso background es el siguiente:
    //1. Se almacena el nuevo empleado en la base de datos
    //2. Se eliminan los datos que tiene la tabla tblEmpleados
    //3. Se llama el método listarEmpleados para consultar nuevamente la bd
    //4. Se cargan los empleados en la tabla incluyendo el recién creado
    public void borrarDatosTabla() {
	// for loop to deleted each element  on each row inside the table 
	for (int i = 0; i < tblEmpleados.getRowCount(); i++) {
	    //removing each row inside the table with removeRow
	    contenidoTablaEmpleados.removeRow(i);
	    i -= 1;
	}
    }
    
    private void listarDepartamentos(){
        String branchFinder = txtFindBranch.getText();
	

	if (branchFinder.isEmpty()){
	    
	    //query 
	    String queryDepartamentos = "SELECT nombreSucursal, nombreDepartamento, CONCAT ('Zona: ',zona, ' - ', tipoCalle,' ',numero1,' #N° ', numero2, ' - ', numero3 ) AS direccion FROM direccion INNER JOIN sucursal  ON idDireccion  = FK_idDireccion;";
	    System.out.println(queryDepartamentos);
	try {
	    connection = conexion.getConnection();
	    //Creating  queryConsulta
	    st = connection.createStatement();
	    // Exe the query from the variable queryConsulta
	    rs = st.executeQuery(queryDepartamentos);
	    // Object to get all elements from the data base to a jtable

	    Object[] departamentos = new Object[3];
	    contenidoTablaDepartamentos = (DefaultTableModel) tblDepartamentos.getModel();
	    // While loop to run every element from the query

	    while (rs.next()) {
		departamentos[0] = rs.getString("nombreSucursal");
		departamentos[1] = rs.getString("nombreDepartamento");
                departamentos[2] = rs.getString("direccion");
		// making a new row on the jtable for each employee
		contenidoTablaDepartamentos.addRow(departamentos);
		//Sout to print each employee data 
		System.out.println("Sucursal: " + departamentos[0] + " , Departamento: " + departamentos[1]);
		tblDepartamentos.setModel(contenidoTablaDepartamentos);
	    }

	} catch (SQLException e) {

	    System.out.println("Error de creación de tabla de departamentos");
	    System.out.println(e);
	}
	
	}else{
	    
	    String querySrcBranch = "SELECT nombreSucursal, nombreDepartamento, CONCAT (zona, ' - ', tipoCalle,' ',numero1,' #N° ', numero2, ' - ', numero3 ) AS direccion  FROM direccion INNER JOIN sucursal  ON idDireccion  = FK_idDireccion WHERE nombreSucursal" + " LIKE '%" + branchFinder + "%' OR nombreDepartamento LIKE '%" + branchFinder+ "%' ;";
	    System.out.println(branchFinder);
	    System.out.println(querySrcBranch);
	    try {
	    connection = conexion.getConnection();
	    //Creating  queryConsulta
	    st = connection.createStatement();
	    // Exe the query from the variable queryConsulta
	    rs = st.executeQuery(querySrcBranch);
	    // Object to get all elements from the data base to a jtable

	    Object[] departamentos = new Object[3];
	    contenidoTablaDepartamentos = (DefaultTableModel) tblDepartamentos.getModel();
	    // While loop to run every element from the query

	    while (rs.next()) {
		departamentos[0] = rs.getString("nombreSucursal");
		departamentos[1] = rs.getString("nombreDepartamento");
                departamentos[2] = rs.getString("direccion");

		// making a new row on the jtable for each employee
		contenidoTablaDepartamentos.addRow(departamentos);
		//Sout to print each employee data 
		System.out.println("Sucursal: " + departamentos[0] + " , Departamento: " + departamentos[1]);
		tblDepartamentos.setModel(contenidoTablaDepartamentos);
	    }

	} catch (SQLException e) {

	    System.out.println("Error de creación de tabla de departamentos");
	    System.out.println(e);
	}
	         
	    
	    
	}
    }

    public void borrarDatosTablaDepartamentos(){
        for (int i = 0; i < tblDepartamentos.getRowCount(); i++) {
	    //removing each row inside the table with removeRow
	    contenidoTablaDepartamentos.removeRow(i);
	    i -= 1;
	}
    }
    
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabHome = new javax.swing.JTabbedPane();
        tabEmployees = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnAddUser = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        btnSearch = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        tabSucursales = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbDepartamento = new javax.swing.JComboBox<>();
        cbZona = new javax.swing.JComboBox<>();
        cbTipoDireccion = new javax.swing.JComboBox<>();
        txtNumero1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNumero2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNumero3 = new javax.swing.JTextField();
        btnListar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDepartamentos = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        txtFindBranch = new javax.swing.JTextField();
        btnFindBranch = new javax.swing.JButton();
        btnPuestosTrabajo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setLocationByPlatform(true);

        jPanel4.setBackground(new java.awt.Color(102, 153, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/newUser.png"))); // NOI18N
        btnAddUser.setText("Add Employee");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("EMPLOYEES DATA");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logo.png"))); // NOI18N

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Last Name", "Document type", "Document", "Email", "Sucursal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmpleados);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/showUser.png"))); // NOI18N
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
        });
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Buscar");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(60, 60, 60)
                            .addComponent(jLabel1))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(112, 112, 112)
                            .addComponent(btnAddUser))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnSearch)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearch)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout tabEmployeesLayout = new javax.swing.GroupLayout(tabEmployees);
        tabEmployees.setLayout(tabEmployeesLayout);
        tabEmployeesLayout.setHorizontalGroup(
            tabEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabEmployeesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabEmployeesLayout.setVerticalGroup(
            tabEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabEmployeesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabHome.addTab("Employees", tabEmployees);

        tabSucursales.setBackground(new java.awt.Color(102, 153, 255));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Buscar");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Departamento");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Zona");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tipo Dirección");

        cbDepartamento.setModel(modelEnumDepartamentos);

        cbZona.setModel(modelEnumZona);

        cbTipoDireccion.setModel(modelEnumTipoCalle);

        jLabel8.setText("#");

        jLabel9.setText("-");

        btnListar.setText("Ver Sucursales");

        tblDepartamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sucursal", "Departamento", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDepartamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDepartamentosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDepartamentos);

        btnGuardar.setText("Save");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });

        btnFindBranch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/showUser.png"))); // NOI18N
        btnFindBranch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFindBranchMouseClicked(evt);
            }
        });

        btnPuestosTrabajo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/iconEmpleado.png"))); // NOI18N
        btnPuestosTrabajo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPuestosTrabajoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tabSucursalesLayout = new javax.swing.GroupLayout(tabSucursales);
        tabSucursales.setLayout(tabSucursalesLayout);
        tabSucursalesLayout.setHorizontalGroup(
            tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSucursalesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(tabSucursalesLayout.createSequentialGroup()
                        .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(34, 34, 34)
                        .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabSucursalesLayout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnListar))
                            .addGroup(tabSucursalesLayout.createSequentialGroup()
                                .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbTipoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFindBranch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnFindBranch)
                                    .addGroup(tabSucursalesLayout.createSequentialGroup()
                                        .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(tabSucursalesLayout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(tabSucursalesLayout.createSequentialGroup()
                                                .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(32, 32, 32)
                                        .addComponent(btnPuestosTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        tabSucursalesLayout.setVerticalGroup(
            tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSucursalesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(btnFindBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtFindBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSucursalesLayout.createSequentialGroup()
                        .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbTipoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabSucursalesLayout.createSequentialGroup()
                        .addComponent(btnPuestosTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(tabSucursalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnListar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        tabHome.addTab("Sucursales", tabSucursales);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabHome, javax.swing.GroupLayout.PREFERRED_SIZE, 623, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabHome, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        borrarDatosTabla();
        listarEmpleados();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        //Creamos una instancia del diálogo
        AddUserForm addUserF = new AddUserForm(this, rootPaneCheckingEnabled);
        addUserF.setVisible(true);
        borrarDatosTabla();
        listarEmpleados();
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void tblEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosMouseClicked
        // get information from row num
        int row = tblEmpleados.getSelectedRow();
        System.out.println("Fila seleccionada: " + row);

        //Validate data
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un  empleado ", " ", JOptionPane.WARNING_MESSAGE);

        } else {
            
            String nombreEmp = tblEmpleados.getValueAt(row, 0).toString();
            String apellidos = tblEmpleados.getValueAt(row, 1).toString();
            String tipoDocumento = tblEmpleados.getValueAt(row, 2).toString();
            String documento = tblEmpleados.getValueAt(row, 3).toString();
            String correo = tblEmpleados.getValueAt(row, 4).toString();
            String sucursal = tblEmpleados.getValueAt(row, 5).toString();
            System.out.println("empleado: " + nombreEmp + " " + apellidos + ", tipo documento: " + tipoDocumento + ", numero: " + documento + ",  correo: " + correo + ",  sucursal: " + sucursal);
            // Instancia para mostrar la información del empleado seleccionado
            ShowUserForm showUserForm = new ShowUserForm(this, true);
            showUserForm.recibirDatos(nombreEmp, apellidos, tipoDocumento, documento, correo, sucursal);
            showUserForm.setVisible(true);
            //Actualizando datos de la tabla
            borrarDatosTabla();
            listarEmpleados();

        }
    }//GEN-LAST:event_tblEmpleadosMouseClicked

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        borrarDatosTabla();
        listarEmpleados();
    }//GEN-LAST:event_btnSearchMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            borrarDatosTabla();
            listarEmpleados();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        String departamentoOption = cbDepartamento.getSelectedItem().toString();
        String zonaOption = cbZona.getSelectedItem().toString();
        String tipoCalleOption = cbTipoDireccion.getSelectedItem().toString();
        String numero1 = txtNumero1.getText();
        String numero2 = txtNumero2.getText();
        String numero3 = txtNumero3.getText();
        String queryAddress = "INSERT INTO `direccion`(`zona`, `tipoCalle`, `numero1`, `numero2`, `numero3`, `nombreDepartamento`) VALUES ('"+zonaOption+"','"+ tipoCalleOption+"','"+numero1+"','"+numero2+"','"+numero3+"','"+departamentoOption+"')";
        System.out.println(queryAddress);
        try {
	    connection = conexion.getConnection();
	    st = connection.createStatement();
	    st.executeUpdate(queryAddress);
            // query idDirecion with msg
	    String queryIdDireccion = "SELECT IdDireccion FROM `direccion` WHERE nombreDepartamento = '" + departamentoOption + "' AND zona ='" + zonaOption + "' AND tipoCalle ='" + tipoCalleOption + "' AND numero1 = '" + numero1 + "' AND numero2 = '" + numero2 + "' AND numero3 = '" + numero3 + "' ;";
	    System.out.println(queryIdDireccion);
            
	    try {
                connection = conexion.getConnection();
                st = connection.createStatement();
		rs = st.executeQuery(queryIdDireccion);
		SucursalForm sucursalForm = new SucursalForm(this, true);
		sucursalForm.setVisible(true);
                while(rs.next()){
                    int idDireccion = rs.getInt("idDireccion");
                    sucursalForm.recibirIdDireccion(idDireccion);
                    System.out.println("Envia Id " + idDireccion);
                }		
		borrarDatosTablaDepartamentos();
		listarDepartamentos();
	    } catch (SQLException e) {
		System.out.println(e);
	    }

	    //Show msg when the registry of a new employee its successful 
	    JOptionPane.showMessageDialog(this, "Sucursal creada exitosamente ", "", JOptionPane.INFORMATION_MESSAGE);

	} catch (SQLException e) {
	    System.out.println(e);
	    JOptionPane.showMessageDialog(this, "No se pudo crear la sucursal", "", JOptionPane.ERROR_MESSAGE);
	}   
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void tblDepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDepartamentosMouseClicked
        int row = tblDepartamentos.getSelectedRow();
	System.out.println("Fila seleccionada: " + row);

	//Validate data
	if (row < 0) {
	    JOptionPane.showMessageDialog(this, "Selecciona una sucursal ", " ", JOptionPane.WARNING_MESSAGE);

	} else {
	    //Get employee (id, name, surname,  id type , id num , e-mail )
	    
	    // row 0 column 1 =  employee name
	    String nombreSucursal = (String) tblDepartamentos.getValueAt(row, 0);
	   
	    // instance for jDialog to show the selected employee info
	    ShowBranchForm showBranchF = new ShowBranchForm(this, true);
	    showBranchF.recibirDatos(nombreSucursal);
	    showBranchF.setVisible(true);

	    //update data from table
	    borrarDatosTablaDepartamentos();
	    listarDepartamentos();
	}
    }//GEN-LAST:event_tblDepartamentosMouseClicked

    private void btnPuestosTrabajoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPuestosTrabajoMouseClicked

        PuestosTrabajoForm puestoTrabajo = new PuestosTrabajoForm(this, true);
        puestoTrabajo.setVisible(true);
    }//GEN-LAST:event_btnPuestosTrabajoMouseClicked

    private void btnFindBranchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFindBranchMouseClicked
        borrarDatosTablaDepartamentos();
        listarDepartamentos();
    }//GEN-LAST:event_btnFindBranchMouseClicked
/**    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
	/* Set the Nimbus look and feel */
	//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
	/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	 */
	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException ex) {
	    java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(() -> {
	    new UserMenu().setVisible(true);
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnFindBranch;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnPuestosTrabajo;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbDepartamento;
    private javax.swing.JComboBox<String> cbTipoDireccion;
    private javax.swing.JComboBox<String> cbZona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel tabEmployees;
    private javax.swing.JTabbedPane tabHome;
    private javax.swing.JPanel tabSucursales;
    private javax.swing.JTable tblDepartamentos;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTextField txtFindBranch;
    private javax.swing.JTextField txtNumero1;
    private javax.swing.JTextField txtNumero2;
    private javax.swing.JTextField txtNumero3;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
