import java.time.LocalDate;
import java.util.Objects;

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

    public int getCodEmp() {
        return CodEmp;
    }

    public void setCodEmp(int codEmp) {
        CodEmp = codEmp;
    }

    public String getCodDep() {
        return CodDep;
    }

    public void setCodDep(String codDep) {
        CodDep = codDep;
    }

    public String getExTelEmp() {
        return ExTelEmp;
    }

    public void setExTelEmp(String exTelEmp) {
        ExTelEmp = exTelEmp;
    }

    public LocalDate getFecInEmp() {
        return FecInEmp;
    }

    public void setFecInEmp(LocalDate fecInEmp) {
        FecInEmp = fecInEmp;
    }

    public LocalDate getFecNaEmp() {
        return FecNaEmp;
    }

    public void setFecNaEmp(LocalDate fecNaEmp) {
        FecNaEmp = fecNaEmp;
    }

    public String getNomEmp() {
        return NomEmp;
    }

    public void setNomEmp(String nomEmp) {
        NomEmp = nomEmp;
    }

    public int getNumHi() {
        return NumHi;
    }

    public void setNumHi(int numHi) {
        NumHi = numHi;
    }

    public double getSalEmp() {
        return SalEmp;
    }

    public void setSalEmp(double salEmp) {
        SalEmp = salEmp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        empleados empleado = (empleados) o;
        return CodEmp == empleado.CodEmp && NumHi == empleado.NumHi && Double.compare(SalEmp, empleado.SalEmp) == 0 && Objects.equals(CodDep, empleado.CodDep) && Objects.equals(ExTelEmp, empleado.ExTelEmp) && Objects.equals(FecInEmp, empleado.FecInEmp) && Objects.equals(FecNaEmp, empleado.FecNaEmp) && Objects.equals(NifEmp, empleado.NifEmp) && Objects.equals(NomEmp, empleado.NomEmp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CodEmp, CodDep, ExTelEmp, FecInEmp, FecNaEmp, NifEmp, NomEmp, NumHi, SalEmp);
    }
    
}
