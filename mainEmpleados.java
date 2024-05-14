import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;

public class mainEmpleados {
    static Scanner sc;
    static Connection cnx;

    static {
        try {
            cnx = getConnexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static Connection getConnexion() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/empleados";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
    public static void main(String[] args) throws SQLException, IOException {

        sc = new Scanner(System.in);
        int opc;
        do {
            menu();
            opc = Integer.parseInt(sc.nextLine());
            switch (opc) {

                case 1:
                    ListarTodos();
                    break;
                case 2:
                    InsertarEmpleado();
                    break;
                case 3:
                    quitarEmp();
                    break;
                case 4:
                    actualizarEmp();
                    break;
                case 5:
                    actEmpCol();
                    break;

                case 6:
                    mostrarColumna();
                    break;

            }
        } while (opc != 0);

    }
    public static void menu() {

        System.out.println(" ");
        System.out.println("SISTEMA DE GESTIÓN DE EMPLEADOS");
        System.out.println("+-*%/+-*%/+-*%/+-*%/+-*%/+-*%/+-*%/+-*%/+-*%/+-*%/+-*%/+-*%/+-*%/+-*%/");
        System.out.println("0. Salir");
        System.out.println("1. Listar todos los empleados");
        System.out.println("2. Insertar empleado nuevo");
        System.out.println("3. Eliminar empleado");
        System.out.println("4. Actualizar por nombre");
        System.out.println("5. Actualizar por campo");
        System.out.println("6. Mostrar datos por campo");
        System.out.println(" ");
    }

    public static void InsertarEmpleado() throws SQLException, IOException {
        Statement stm = cnx.createStatement();


        PreparedStatement ps = cnx.prepareStatement(
                "INSERT INTO empleado (CodEmp,CodDep,ExTelEmp,FecInEmp,FecNaEmp,NifEmp,NomEmp,NumHi,SalEmp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Escribe el NIF del empleado.");

        String nif = br.readLine();
        if(findNIF(nif) != null){
            System.out.println("Este NIF ya esta en uso, revise los empleados existentes");

            return;
        }
        ps.setString(5, nif);

        System.out.println("Ingrese el codigo del departamento.");
        ps.setString(1, br.readLine());

        System.out.println("Ingrese el telefono del empleado.");
        ps.setString(2, br.readLine());

        System.out.println("Ingrese la fecha de inicio del empleado (dd/mm/aaaa).");
        String fechaInicio = br.readLine();

        LocalDate FecIn = LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        ps.setDate(3, Date.valueOf(FecIn));

        System.out.println("Ingrese el fecha de nacimiento del empleado (dd/mm/aaaa).");
        String fechaNacimiento = br.readLine();

        LocalDate FecNa = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        ps.setDate(4, Date.valueOf(FecNa));

        System.out.println("Ingrese el nombre del empleado.");
        ps.setString(6, br.readLine());

        System.out.println("Ingrese el numero de hijos del empleado.");
        ps.setInt(7, Integer.parseInt(br.readLine()));
        System.out.println("Ingrese el sueldo del empleado.");

        ps.setDouble(8, Double.parseDouble(br.readLine()));
        ps.executeUpdate();
        ps.close();

        System.out.println("Actualizado, volviendo al menú");
        menu();
    }

    public static void ListarTodos() throws SQLException {

        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM empleado");
        while(rs.next()){
            for(int i = 1; i <= 9;i++){
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
    }

    public static void actualizarEmp() throws SQLException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingrese el nombre del empleado.");
        String nombre = br.readLine();
        empleados e = findName(nombre);
        if(e == null){
            System.out.println("Este empleado no existe");
            return;
        }
        System.out.println("Escribe el nuevo codigo departamento del empleado.");
        e.setCodDep(sc.next());
        System.out.println("Escribe el nuevo telefono del empleado.");
        e.setExTelEmp(sc.next());
        System.out.println("Escribe la nueva fecha de inicio del empleado (dd/mm/aaaa).");
        String FechaInicio = br.readLine();
        LocalDate FecIn = LocalDate.parse(FechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        e.setFecInEmp(FecIn);
        System.out.println("Escribe la nueva fecha de nacimiento del empleado.");
        String FechaNacimiento = br.readLine();
        LocalDate FecNa = LocalDate.parse(FechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        e.setFecNaEmp(FecNa);
        System.out.println("Escribe el nuevo nif del empleado.");
        String Nif = br.readLine();
        e.setNifEmp(Nif);
        System.out.println("Escribe el nuevo nombre del empleado.");
        String newNom = br.readLine();
        System.out.println("Escribe el nuevo numero de hijos del empleado.");
        e.setNumHi(sc.nextInt());
        System.out.println("Escribe el nuevo sueldo del empleado.");
        e.setSalEmp(sc.nextDouble());

        try{   //Probar
            actualiza(e,newNom);
            System.out.println("El empleado se ha actualizado.");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void quitarEmp() throws SQLException, IOException {
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM empleado WHERE NifEmp = ?");
        System.out.println("Escribe el nif del empleado.");
        String nif = sc.next();
        if(findNIF(nif) == null){
            System.out.println("El empleado no existe");
            return;
        }
        ps.setString(1, nif);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            PreparedStatement pss = cnx.prepareStatement("DELETE FROM empleado WHERE NifEmp = ?");
            pss.setString(1, nif);
            pss.executeUpdate();
            pss.close();
            System.out.println("Empleado eliminado con exito. (No murió lo juro)");
        }
        ps.close();
    }

    public static empleados findNIF(String nif) throws SQLException{
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM empleado WHERE NifEmp = ?");
        ps.setString(1, nif);
        ResultSet rs = ps.executeQuery();
        empleados e = null;
        while(rs.next()){
            e = new empleados(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getDate(4).toLocalDate(),rs.getDate(5).toLocalDate(),rs.getString(6),rs.getString(7),rs.getInt(8),rs.getDouble(9));
        }
        return e;
    }

    public static void mostrarColumna() throws SQLException,IOException {
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM empleado");
        System.out.println("Seleccione el campo que desea ver:\n 1 - Codigo de empleado\n 2 - Codigo departamento.\n 3 - Telefono del empleado.\n 4 - Fecha de inicio.\n 5 - Fecha de nacimiento.\n 6 - NIF del empleado.\n 7 - Nombre del empleado.\n 8 - Numero de hijos del empleado.\n 9 - Sueldo del empleado.");
        int option = sc.nextInt();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString(option));
        }
        rs.close();
        ps.close();
    }



    public static empleados findName(String nom) throws SQLException{
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM empleado WHERE NomEmp = ?");
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();
        empleados e = null;
        while(rs.next()){
            e = new empleados(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getDate(4).toLocalDate(),rs.getDate(5).toLocalDate(),rs.getString(6),rs.getString(7),rs.getInt(8),rs.getDouble(9));
        }
        return e;
    }

    public static void actualiza(empleados e,String nom) throws SQLException{


        PreparedStatement ps = cnx.prepareStatement("UPDATE empleado SET CodEmp = ?, CodDep = ?, ExTelEmp = ?, FecInEmp = ?, FecNaEmp = ?, NifEmp = ?, NomEmp = ?, NumHi = ?, SalEmp = ? WHERE NomEmp = ?");

        ps.setInt(1, e.getCodEmp());
        ps.setString(2, e.getCodDep());
        ps.setString(3, e.getExTelEmp());
        ps.setDate(4,Date.valueOf(e.getFecInEmp()));
        ps.setDate(5,Date.valueOf(e.getFecNaEmp()));
        ps.setString(6,e.getNifEmp());
        ps.setString(7, nom);
        ps.setInt(8, e.getNumHi());
        ps.setDouble(9,e.getSalEmp());
        ps.setString(10,e.getNomEmp());
        ps.executeUpdate();
        ps.close();
    }

    public static void actEmpCol()throws SQLException,IOException{
        System.out.println("NIF del empleado.");
        String nif = sc.next();
        empleados e = findNIF(nif);
        if(e == null){
            System.out.println("Este empleado no existe");
            return;
        }
        System.out.println("Seleccione el campo a modificar:\n 1 - Codigo departamento.\n 2 - Telefono del empleado.\n 3 - Fecha de inicio.\n 4 - Fecha de nacimiento.\n 5 - NIF del empleado.\n 6 - Nombre del empleado.\n 7 - Numero de hijos del empleado.\n 8 - Sueldo del empleado.");
        int option = sc.nextInt();
        switch(option){

            case 1 ->{
                System.out.println("Nuevo codigo de departamento .");
                String CodDep = sc.next();
                e.setCodDep(CodDep);
                actualiza(e,e.getNomEmp());
            }
            case 2 ->{
                System.out.println("Nuevo telefono .");
                String TelEmp = sc.next();
                e.setExTelEmp(TelEmp);
                actualiza(e,e.getNomEmp());
            }
            case 3 ->{
                System.out.println("Nuevo fecha de inicio .");
                String FechaInicio = sc.next();
                LocalDate FecIn = LocalDate.parse(FechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                e.setFecInEmp(FecIn);
                actualiza(e,e.getNomEmp());
            }
            case 4 ->{
                System.out.println("Nuevo fecha de nacimiento .");
                String FechaNacimiento = sc.next();
                LocalDate FecNa = LocalDate.parse(FechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                e.setFecNaEmp(FecNa);
                actualiza(e,e.getNomEmp());
            }
            case 5 ->{
                System.out.println("Nuevo NIF .");
                String Nif = sc.next();
                e.setNifEmp(Nif);
                actualiza(e,e.getNomEmp());
            }
            case 6 ->{
                System.out.println("Nuevo nombre .");
                String Nombre = sc.next();
                e.setNomEmp(Nombre);
                actualiza(e,e.getNomEmp());
            }
            case 7 ->{
                System.out.println("Nueva cantidad de hijos.");
                int NumHi = sc.nextInt();
                e.setNumHi(NumHi);
                actualiza(e,e.getNomEmp());
            }
            case 8 ->{
                System.out.println("Nuevo sueldo .");
                double Sueldo = sc.nextDouble();
                e.setSalEmp(Sueldo);
                actualiza(e,e.getNomEmp());
            }
        }
        System.out.println("Datos registrados exitosamente.");
    }
}
