package controller;

import client.communication.Communication;
import communication.Operations;
import communication.Request;
import communication.Response;
import communication.ResponseType;
import domain.*;

import java.util.ArrayList;
import java.util.List;


public class Controller {
    private static Controller instance;
    private Korisnik ulogovaniKorisnik;
    private Osoba izabranaOsoba;
    private Trotinet izabraniTrotinet;
    private List<IznajmljivanjeTrotineta> izabraneVoznje = new ArrayList<>();



    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Korisnik login(String username, String password) throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setUsername(username);
        korisnik.setSifra(password);

        Request request = new Request(Operations.LOGIN, korisnik);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            Korisnik k = (Korisnik) response.getResult();
            return k;
        } else {
            throw response.getException();
        }
    }

    public void setUlogovaniKorisnik(Korisnik korisnik) {
        this.ulogovaniKorisnik = korisnik;
    }

    public Korisnik getUlogovanKorisnik() {
        return ulogovaniKorisnik;
    }

    public void setIzabranaOsoba(Osoba izabranaOsoba) {
        this.izabranaOsoba = izabranaOsoba;
    }

    public Osoba getIzabranaOsoba() {
        return izabranaOsoba;
    }

    public void setIzabraniTrotinet(Trotinet izabraniTrotinet) {
        this.izabraniTrotinet = izabraniTrotinet;
    }

    public Trotinet getIzabraniTrotinet() {
        return izabraniTrotinet;
    }

    public void addVoznja(IznajmljivanjeTrotineta voznja) throws Exception {
        Request request = new Request(Operations.ADD_VOZNJA, voznja);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno kreirana voznja!");
        } else {
            throw response.getException();
        }
    }

    public void addTrotinet(Trotinet trotinet) throws Exception {
        Request request = new Request(Operations.ADD_TROTINET, trotinet);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno kreiran trotinet!");
        } else {
            throw response.getException();
        }
    }

    public List<Trotinet> getAllTrotinet() throws Exception {
        Request request = new Request(Operations.GET_ALL_TROTINET, null);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno vracena lista trotineta!");
            return (List<Trotinet>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Trotinet> getAllByVrsta(VrstaTrotinetaEnum vrstaTrotineta) throws Exception {
        Request request = new Request(Operations.GET_TROTINET_BY_VRSTA, vrstaTrotineta);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno vracena lista trotineta!");
            return (List<Trotinet>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public Trotinet getTrotinetById(Long trotinetID) throws Exception {
        Request request = new Request(Operations.GET_TROTINET_BY_ID, trotinetID);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno vracen trotinet!");
            return (Trotinet) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<IznajmljivanjeTrotineta> getAllVoznje() throws Exception {
        Request request = new Request(Operations.GET_ALL_VOZNJE, null);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno vracena lista trotineta!");
            return (List<IznajmljivanjeTrotineta>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<IznajmljivanjeTrotineta> getAllByCriteria(String username) throws Exception {
        Request request = new Request(Operations.GET_ALL_VOZNJE_BY_CRITERIA, username);
        Response response = null;
        response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno vracena lista voznji po kriterijumu!");
            return (List<IznajmljivanjeTrotineta>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void addAllVoznje(List<IznajmljivanjeTrotineta> voznje) throws Exception{
        Request request = new Request(Operations.ADD_ALL_VOZNJE, voznje);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno kreirane voznje!");
        } else {
            throw response.getException();
        }
    }

    public void deleteTrotinet(Long trotinetID) throws Exception {
        Request request = new Request(Operations.DELETE_TROTINET, trotinetID);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno brisanje voznje!");
        } else {
            throw response.getException();
        }
    }

    public List<Osoba> getAllOsoba() throws Exception {
        Request request = new Request(Operations.GET_ALL_OSOBE, null);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno brisanje voznje!");
            return (List<Osoba>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void addOsoba(Osoba osoba) throws Exception {
        Request request = new Request(Operations.ADD_OSOBA, osoba);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno kreirana osoba!");
        } else {
            throw response.getException();
        }
    }

    public void addKorisnik(Korisnik korisnik) throws Exception {
        Request request = new Request(Operations.ADD_KORISNIK, korisnik);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno kreiran korisnik!");
        } else {
            throw response.getException();
        }
    }

    public List<Korisnik> getAllKorisnik() throws Exception{
        Request request = new Request(Operations.GET_ALL_KORISNIK, null);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno ucitavanje liste korisnika!");
            return (List<Korisnik>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Korisnik> getAllByUsername(String username) throws Exception{
        Request request = new Request(Operations.GET_ALL_KORISNIK_BY_USERNAME, username);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno vracena lista korisnika po kriterijumu!");
            return (List<Korisnik>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void deleteKorisnik(String username) throws Exception {
        Request request = new Request(Operations.DELETE_KORISNIK, username);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno brisanje korisnika!");
        } else {
            throw response.getException();
        }
    }

    public List<Osoba> getByBrojLK(Long brojLK) throws Exception {
        Request request = new Request(Operations.GET_ALL_OSOBE_BY_BROJ_LK, brojLK);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno vracena lista osoba po kriterijumu!");
            return (List<Osoba>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void updateKorisnik(Korisnik korisnik) throws Exception{
        Request request = new Request(Operations.UPDATE_KORISNIK, korisnik);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno izmenjen korisnik!");
        } else {
            throw response.getException();
        }
    }

    public void updateTrotinet(Trotinet trotinet) throws Exception {
        Request request = new Request(Operations.UPDATE_TROTINET, trotinet);
        Response response = Communication.getInstance().makeRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            System.out.println("Uspesno izmenjen trotinet!");
        } else {
            throw response.getException();
        }
    }


    public List<IznajmljivanjeTrotineta> getIzabraneVoznje() {
        return izabraneVoznje;
    }

    public void setIzabraneVoznje(List<IznajmljivanjeTrotineta> izabraneVoznje) {
        this.izabraneVoznje = izabraneVoznje;
    }
}
