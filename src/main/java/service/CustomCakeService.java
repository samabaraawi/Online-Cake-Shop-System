package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CustomCake;

public class CustomCakeService {

    private static final CustomCakeService instance =
            new CustomCakeService();

    private final ObservableList<CustomCake> requests =
            FXCollections.observableArrayList();

    private CustomCake currentRequest;

    private CustomCakeService() {

    }

    public static CustomCakeService getInstance() {

        return instance;

    }

    public void submitRequest(CustomCake request) {

        request.setRequestStatus("Pending");

        currentRequest = request;

        requests.add(request);

    }

    public CustomCake getCurrentRequest() {

        return currentRequest;

    }

    public ObservableList<CustomCake> getAllRequests() {

        return requests;

    }

}