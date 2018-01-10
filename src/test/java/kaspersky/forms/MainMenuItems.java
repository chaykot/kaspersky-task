package kaspersky.forms;

public enum MainMenuItems {
    DEVICES("Devices"),
    LICENSES("Licenses"),
    SUPPORT("Support"),
    DOWNLOADS("Downloads"),
    PASSWORDS("Passwords"),
    KIDS("Kids"),
    STORE("Store");

    private String mainMenuItem;

    MainMenuItems(String mainMenuItem) {
        this.mainMenuItem = mainMenuItem;
    }

    @Override
    public String toString() {
        return mainMenuItem;
    }
}

