import java.io.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Samuel Matthews
 *
 * No copyright infringements intended, holds no copyright.
 *
 * This class holds all of the instructions for the Browser.fxml file
 * and loads all saved resources out of the ResourceDatabase.txt file
 * while enabling the user to search through these resources using
 * search terms and filters. This is also a gateway for the user to
 * access the ViewResource GUI that shows more specific details about
 * the resource. Information about the resource is limited so the user
 * is not confused with the random words and numbers that the text files
 * hold.
 *
 * @version 3.0
 * @since 30/11/2018
 */

public class BrowserController {
    @FXML TextField searchParam; //The space for the user to enter their search term.
    @FXML Button searchButton; //The button that is pressed to initialize the search.
    @FXML Button returnButton; //The button that is pressed to return to the dashboard.
    @FXML Button viewResourceButton; //The button that is pressed to view a resource.
    @FXML MenuButton filterOptions; //The filters allowed by the GUI.
    @FXML ListView<String> searchResults; //The results of the search.

    BufferedReader resourceBR = null; //The BufferedReader used throughout the class.
    final String DATABASE_LOCATION = "Data/resourceDatabase.txt"; //Location of database.
    File resourceDatabase = new File(DATABASE_LOCATION); //The file to be loaded into BufferedReader.
    final String SELECTED_LOCATION = "Data/selectedResource.txt"; //Location of the selected resource.
    File selectedResource = new File(SELECTED_LOCATION); //The file to be loaded into BufferedReader.

    String searchTerm; //The search term collected from the searchParam text field.

    //The data that is shown to the viewer from every resource.
    String rType;
    String rID;
    String rTitle;
    String rYear;
    CurrentSessionData temp = new CurrentSessionData();
    String userName;
    String userType;

    /**
     * Initializes the fxml file by calling both the refreshSearchResults() and the
     * handleAllFilter() that set the GUI up so that no search results are kept
     * from any past use.
     */
    public void initialize() {
        handleAllFilter();
        refreshSearchResults();
        userName = temp.getUsername();
        userType = temp.getUserType();
    }

    /**
     * Clears any search results from any past use and then proceeds to load the
     * ResourceDatabase.txt file for the rest of the class to use then and display
     * all saved resources on the screen. Only the type, ID, title and year are
     * printed as we split the result by our delimiter (";") and print the first 4
     * items in the line that correlate to these respective items. If the
     * ResourceDatabase cannot be found for any reason then an error message
     * will print before the program closes.
     */
    @FXML
    private void refreshSearchResults() {
        searchResults.getItems().clear();
        try {
            resourceBR = new BufferedReader(new FileReader(resourceDatabase));

            String line = null; //The current line that the BufferedReader is on.

            while ((line = resourceBR.readLine()) != null) {
                String[] lineSplit = line.split(";");
                rType = lineSplit[0];
                rID = lineSplit[1];
                rTitle = lineSplit[2];
                rYear = lineSplit[3];
                searchResults.getItems().add(rType + " - " + rID + " - " + rTitle +
                        " - " + rYear);
            }
        } catch (IOException e) {
            System.out.println("Cannot open ResourceDatabase");
            System.exit(0);
        }
    }

    /**
     * The main bulk of functionality for this class; the search function starts
     * by clearing the search results from the window before retrieving the search
     * term from the text field in the GUI. The file is then reloaded into the
     * controller. If the search term does not equal null when a search is made,
     * then the search term is stored as a variable. The ResourceDatabase is then
     * looped through. If the line contains the search term, then the filters are
     * checked to only show resources that are of the specified resource type. If
     * the search term equals null, then the filters are checked to show all resources
     * of the type specified.
     */
    @FXML
    private void handleSearchEvent() {
        searchResults.getItems().clear();
        searchTerm = searchParam.getText().toLowerCase();

        try {
            resourceBR = new BufferedReader(new FileReader(resourceDatabase));

            String line; //The current line that the BufferedReader is on.

            if (searchTerm != null) {
                while ((line = resourceBR.readLine()) != null) {
                    if (line.toLowerCase().contains(searchTerm)) {
                        if (filterOptions.getText() == "All") {
                            String[] lineSplit = line.split(";");
                            rType = lineSplit[0];
                            rID = lineSplit[1];
                            rTitle = lineSplit[2];
                            rYear = lineSplit[3];
                            searchResults.getItems().add(rType + " - " + rID + " - " + rTitle + " - " + rYear);
                        } else if (filterOptions.getText() == "Book") {
                            if (line.contains("book")) {
                                String[] lineSplit = line.split(";");
                                rType = lineSplit[0];
                                rID = lineSplit[1];
                                rTitle = lineSplit[2];
                                rYear = lineSplit[3];
                                searchResults.getItems().add(rType + " - " + rID + " - " + rTitle + " - " + rYear);
                            }
                        } else if (filterOptions.getText() == "Laptop") {
                            if (line.contains("laptop")) {
                                String[] lineSplit = line.split(";");
                                rType = lineSplit[0];
                                rID = lineSplit[1];
                                rTitle = lineSplit[2];
                                rYear = lineSplit[3];
                                searchResults.getItems().add(rType + " - " + rID + " - " + rTitle + " - " + rYear);
                            }
                        } else if (filterOptions.getText() == "DVD") {
                            if (line.contains("dvd")) {
                                String[] lineSplit = line.split(";");
                                rType = lineSplit[0];
                                rID = lineSplit[1];
                                rTitle = lineSplit[2];
                                rYear = lineSplit[3];
                                searchResults.getItems().add(rType + " - " + rID + " - " + rTitle + " - " + rYear);
                            }
                        }
                    }

                }
            } else {
                while ((line = resourceBR.readLine()) != null) {
                    if (filterOptions.getText() == "Filters" || filterOptions.getText() == "All") {
                        String[] lineSplit = line.split(";");
                        rType = lineSplit[0];
                        rID = lineSplit[1];
                        rTitle = lineSplit[2];
                        rYear = lineSplit[3];
                        searchResults.getItems().add(rType + " - " + rID + " - " + rTitle + " - " + rYear);
                    } else if (filterOptions.getText() == "Book") {
                        if (searchResults.getItems().contains("book")) {
                            String[] lineSplit = line.split(";");
                            rType = lineSplit[0];
                            rID = lineSplit[1];
                            rTitle = lineSplit[2];
                            rYear = lineSplit[3];
                            searchResults.getItems().add(rType + " - " + rID + " - " + rTitle + " - " + rYear);
                        }
                    } else if (filterOptions.getText() == "Laptop") {
                        if (searchResults.getItems().contains("laptop")) {
                            String[] lineSplit = line.split(";");
                            rType = lineSplit[0];
                            rID = lineSplit[1];
                            rTitle = lineSplit[2];
                            rYear = lineSplit[3];
                            searchResults.getItems().add(rType + " - " + rID + " - " + rTitle + " - " + rYear);
                        }
                    } else if (filterOptions.getText() == "DVD") {
                        if (searchResults.getItems().contains("dvd")) {
                            String[] lineSplit = line.split(";");
                            rType = lineSplit[0];
                            rID = lineSplit[1];
                            rTitle = lineSplit[2];
                            rYear = lineSplit[3];
                            searchResults.getItems().add(rType + " - " + rID + " - " + rTitle + " - " + rYear);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot open ResourceDatabase");
        }
    }

    /**
     * Sets the text of the filter button to "All"
     */
    @FXML
    private void handleAllFilter() {
        filterOptions.setText("All");
        handleSearchEvent();
    }

    /**
     * Sets the text of the filter button to "Book"
     */
    @FXML
    private void handleBookFilter() {
        filterOptions.setText("Book");
        handleSearchEvent();
    }

    /**
     * Sets the text of the filter button to "Laptop"
     */
    @FXML
    private void handleLaptopFilter() {
        filterOptions.setText("Laptop");
        handleSearchEvent();
    }

    /**
     * Sets the text of the filter button to "DVD"
     */
    @FXML
    private void handleDVDFilter() {
        filterOptions.setText("DVD");
        handleSearchEvent();
    }

    /**
     * If the user selects (clicks on) a resource and then presses the
     * 'View Resource' button, then a new window is opened to show more
     * specific details about the selected resource. If no resource is
     * selected then an Exception is thrown.
     *
     * @throws Exception
     */
    @FXML
    private void handleViewResourceEvent() throws Exception {
        int selectedIndex = searchResults.getSelectionModel().getSelectedIndex();

        if (selectedIndex < 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error: Cannot find selection");
            alert.setHeaderText(null);
            alert.setContentText("Cannot view Resource as no Resource was selected. Please select a resource to view");
            alert.showAndWait();
        } else {
            String selectedLine = searchResults.getSelectionModel().getSelectedItem();
            String[] selectedSplit = selectedLine.split(" - ");
            String selectedID = selectedSplit[1];
            System.out.println(selectedID);
            String resourceString = null;

            try {
                resourceBR = new BufferedReader(new FileReader(resourceDatabase));

                String line = null; //The current line that the BufferedReader is on.

                while ((line = resourceBR.readLine()) != null) {
                    if (line.contains(selectedID)) {
                        resourceString = line;
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }


            BufferedWriter writeSelected = new BufferedWriter(new FileWriter(selectedResource));
            writeSelected.write(resourceString);

            writeSelected.flush();
            writeSelected.close();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ResourceView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
    	        Stage stage = new Stage();
    	        stage.setScene(new Scene(root));  
    	        stage.show();
                
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes this window in order to show the dashboard for the user.
     */
    @FXML
    private void handleReturnEvent() {
        Stage stage = (Stage) returnButton.getScene().getWindow();
        stage.close();
    }
}