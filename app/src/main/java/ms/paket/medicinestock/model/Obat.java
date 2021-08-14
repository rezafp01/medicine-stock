package ms.paket.medicinestock.model;

public class Obat {

    private String NamaObat;
    private String DesObat;
    private  String ImgObat;
    private String AturPakai;
    private String CaraPakai;
    private String Stok;
    private String Apotek;
    private String Search;
    private String key;
    private int id;


    public Obat(String namaObat, String desObat, String imgObat, String aturPakai, String caraPakai, String stok, String apotek) {
        NamaObat = namaObat;
        DesObat = desObat;
        ImgObat = imgObat;
        AturPakai = aturPakai;
        CaraPakai = caraPakai;
        Stok = stok;
        Apotek = apotek;
    }

    public Obat() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaObat() {
        return NamaObat;
    }

    public void setNamaObat(String namaObat) {
        NamaObat = namaObat;
    }

    public String getDesObat() {
        return DesObat;
    }

    public void setDesObat(String desObat) {
        DesObat = desObat;
    }

    public String getImgObat() {
        return ImgObat;
    }

    public void setImgObat(String imgObat) {
        ImgObat = imgObat;
    }

    public String getAturPakai() {
        return AturPakai;
    }

    public void setAturPakai(String aturPakai) {
        AturPakai = aturPakai;
    }

    public String getCaraPakai() {
        return CaraPakai;
    }

    public void setCaraPakai(String caraPakai) {
        CaraPakai = caraPakai;
    }

    public String getStok() {
        return Stok;
    }

    public void setStok(String stok) {
        Stok = stok;
    }

    public String getApotek() {
        return Apotek;
    }

    public void setApotek(String apotek) {
        Apotek = apotek;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " "+NamaObat+"\n" +
                " "+DesObat+"\n" +
                " "+ImgObat+"\n" +
                " "+AturPakai+"\n" +
                " "+CaraPakai+"\n" +
                " "+Stok+ "\n"+
                " "+Apotek+" " ;
    }


}
