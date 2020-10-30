package apap.tugas.sipil.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "penerbangan")
public class PenerbanganModel implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Size(max = 16)
    @Column(name = "kode")
    private String kode;

    @NotNull
    @Size(max = 255)
    @Column(name = "kota_asal")
    private String kotaAsal;

    @NotNull
    @Size(max = 255)
    @Column(name = "kota_tujuan")
    private String kotaTujuan;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "waktu", nullable = false)
    private Date waktu;

    @OneToMany(mappedBy = "penerbanganModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PilotPenerbanganModel> penerbanganPilot;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getKotaAsal() {
        return kotaAsal;
    }

    public void setKotaAsal(String kotaAsal) {
        this.kotaAsal = kotaAsal;
    }

    public String getKotaTujuan() {
        return kotaTujuan;
    }

    public void setKotaTujuan(String kotaTujuan) {
        this.kotaTujuan = kotaTujuan;
    }

    public Date getWaktu() {
        return waktu;
    }

    public void setWaktu(Date waktu) {
        this.waktu = waktu;
    }

    public List<PilotPenerbanganModel> getPenerbanganPilot() {
        return penerbanganPilot;
    }

    public void setPenerbanganPilot(List<PilotPenerbanganModel> penerbanganPilot) {
        this.penerbanganPilot = penerbanganPilot;
    }
}
