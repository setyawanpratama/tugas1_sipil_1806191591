package apap.tugas.sipil.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "maskapai")
public class MaskapaiModel implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama")
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "kode")
    private String kode;

    @OneToMany(mappedBy = "maskapaiModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PilotModel> listPilotMaskapai;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public List<PilotModel> getListPilotMaskapai() {
        return listPilotMaskapai;
    }

    public void setListPilotMaskapai(List<PilotModel> listPilotMaskapai) {
        this.listPilotMaskapai = listPilotMaskapai;
    }
}

