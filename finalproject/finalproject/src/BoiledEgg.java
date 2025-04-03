public class BoiledEgg {
    private String type;
    private int boilTime; //set time in minutes

    public BoiledEgg(String type) {
        this.type = type;
        setBoilTime(type);
    }

    //options for how long to boil the egg
    // soft = 6 minutes, medium = 8 minutes, hard = 10 minutes  
    private void setBoilTime(String type) {
        switch (type) {
            case "soft":
                this.boilTime = 6;
                break;
            case "medium":
                this.boilTime = 8;
                break;
            case "hard":
                this.boilTime = 10;
                break;
            default:
                throw new IllegalArgumentException("Invalid egg type: " + type);
        }
    }

    public int getBoilTime() {
        return boilTime;
    }
    public String getType() {
        return type;
    }
}
