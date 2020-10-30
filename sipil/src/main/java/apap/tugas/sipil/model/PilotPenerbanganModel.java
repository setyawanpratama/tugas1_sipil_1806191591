package apap.tugas.sipil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pilot_penerbangan")
public class PilotPenerbanganModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_penugasan", nullable = false)
    private Date tanggalPenugasan;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pilot", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PilotModel pilotModel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_penerbangan", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PenerbanganModel penerbanganModel;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Date getTanggalPenugasan() {
        return tanggalPenugasan;
    }

    public void setTanggalPenugasan(Date tanggalPenugasan) {
        this.tanggalPenugasan = tanggalPenugasan;
    }

    public PilotModel getPilotModel() {
        return pilotModel;
    }

    public void setPilotModel(PilotModel pilotModel) {
        this.pilotModel = pilotModel;
    }

    public PenerbanganModel getPenerbanganModel() {
        return penerbanganModel;
    }

    public void setPenerbanganModel(PenerbanganModel penerbanganModel) {
        this.penerbanganModel = penerbanganModel;
    }
}
