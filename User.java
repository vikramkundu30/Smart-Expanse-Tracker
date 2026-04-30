public class User {

    private int id;
    private String username;
    private String photoPath;

    public User(int id, String username, String photoPath) {
        this.id = id;
        this.username = username;
        this.photoPath = photoPath;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPhotoPath() { return photoPath; }
}