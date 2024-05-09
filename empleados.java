import java.time.LocalDate;

public class empleados {

    private int CodEmp;
    private String CodDep;
    private String ExTelEmp;
    private LocalDate FecInEmp;
    private LocalDate FecNaEmp;
    private String NifEmp;
    private String NomEmp;
    private int NumHi;
    private double SalEmp;

    public String getNifEmp() {
        return NifEmp;
    }

    public void setNifEmp(String nifEmp) {
        NifEmp = nifEmp;
    }

    public empleados(int codEmp, String codDep, String exTelEmp, LocalDate fecInEmp , LocalDate fecNaEmp, String nifEmp, String nomEmp, int numHi, double SalEmp ) {

        this.CodEmp = codEmp;
        this.CodDep = codDep;
        this.ExTelEmp = exTelEmp;
        this.FecInEmp = fecInEmp;
        this.FecNaEmp = fecNaEmp;
        this.NifEmp = nifEmp;
        this.NomEmp = nomEmp;
        this.NumHi = numHi;
        this.SalEmp = SalEmp;

    }





}
