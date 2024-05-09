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

    public static void main(String[] args) throws SQLException, IOException {


            sc = new Scanner(System.in);
            int opcion;
            do {
                menu();
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 0:

                        break;
                    case 1:
                        ListarTodos();
                        break;
                    case 2:
                        InsertarEmpleado();
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;

                    case 6:

                        break;
                }
            } while (opcion != 0);

    }
    private static Connection getConnexion() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/empleados";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }

    public static void menu() {

        System.out.println("");
        System.out.println("SISTEMA DE GESTIÓN DE Empleados");
        System.out.println("===============================");
        System.out.println("0. Salir");
        System.out.println("1. Listar todos los empleados");
        System.out.println("2. Insertar empleado nuevo");
        System.out.println("3. Eliminar empleado");
        System.out.println("4. Actualizar por nombre");
        System.out.println("5. Actualizar por campo");
        System.out.println("6. Mostrar datos por campo");
    }

    public static void ListarTodos() throws SQLException {

        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * from empleado");
        System.out.println("Todos los datos de la tabla empleado :");
        while (rs.next()) {
            System.out.println(rs.getString(1)+"; " + rs.getString(2)+"; " + rs.getString(3)+"; " + rs.getString(4) +"; "+ rs.getString(5) +"; "+ rs.getString(6)+"; " + rs.getString(7)+"; " + rs.getString(8)+"; " + rs.getString(9) );
        }
        rs.close();
        stm.close();

    }

    public static void InsertarEmpleado() throws SQLException, IOException {
        Statement stm = cnx.createStatement();


            PreparedStatement ps = cnx.prepareStatement(
                    "INSERT INTO empleado (CodEmp,CodDep,ExTelEmp,FecInEmp,FecNaEmp,NifEmp,NomEmp,NumHi,SalEmp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Utilizar esta clase para leer de la entrada exige una claúsula Catch (IO Exception)
            // Es una alternativa a la clase Scanner

        Scanner br = new Scanner(new InputStreamReader(System.in));

        System.out.println("Escribe el codigo del empleado");
            int CodEmp = br.nextInt();

        System.out.println("Escribe el codigo del departamento");
            String coddep = br.next();

        System.out.println("Escribe la estension telefonica del empleado");
            String extelemp = br.next();


        System.out.println("Escribe la fechade inincio del empleado (dd/mm/aaaa)");
            String strFecha = br.next();
            LocalDate fecinemp = LocalDate.parse(strFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("Escribe la fechade nacimiento del empleado  (dd/mm/aaaa)");
        String strFecha2 = br.next();
        LocalDate fecnaemp = LocalDate.parse(strFecha2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));


        System.out.println("Escribe el nif del empleado");
        String nif = String.valueOf(br.next());

    /*     empleados emp = null;
        emp.setNifEmp(nif);

       while (findEmpleado(emp)){

            System.out.println("Ese Nif le pertenece a otro usuario, escribelo otra vez");
            nif = br.nextLine();

        }*/

        System.out.println("Escribe el nombre del empleado");
        String nomemp = br.next();

        System.out.println("Escribe el numero de hijos del empleado");
        int numhiemp = br.nextInt();

        System.out.println("Escribe el salario del empleado");
        double salemp = br.nextDouble();

            ps.setInt(1, CodEmp);
            ps.setString(2, coddep);
            ps.setString(3, extelemp);
            ps.setDate(4, Date.valueOf(fecinemp));
            ps.setDate(5, Date.valueOf(fecnaemp));
            ps.setString(6, nif);
            ps.setString(7, nomemp);
            ps.setInt(8, numhiemp);
            ps.setDouble(9, salemp);
            ps.executeUpdate();
            ps.close();

    }


    public static boolean findEmpleado(empleados Emp) throws SQLException, IOException {

        Statement stm = cnx.createStatement();

        ResultSet rs = stm.executeQuery("SELECT * from empleado where NifEmp like ?");

        empleados empnull = null;

        while (rs.next()) {

            empnull.setNifEmp(rs.getString(6));

            if (empnull.getNifEmp().equals(Emp.getNifEmp())) {
                return true; //Encuentra otro empleado con el mismo nif
            }
        }
        return false; //No encuentra otro empleado con el mismo nif
    }






}


