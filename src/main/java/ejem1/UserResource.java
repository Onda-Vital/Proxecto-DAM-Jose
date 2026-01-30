package ejem1;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.*;

@Path("/user")
public class UserResource {
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Axustamos as credenciais
    private static final String URL = "jdbc:mariadb://localhost:3306/xclone";
    private static final String USER = "root";
    private static final String PASS = "tcpip";

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEmail(@PathParam("email") String email) {
        String sql = "SELECT id, username, email FROM users WHERE email = ? LIMIT 1";

        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User(
                            rs.getLong("id"),
                            rs.getString("username"),
                            rs.getString("email"));
                    return Response.ok(u).build(); // 200
                }
                return Response.status(Response.Status.NOT_FOUND).build(); // 404
            }

        } catch (SQLException e) {
            return Response.status(500)
                    .entity("{\"error\":\"" + e.getMessage().replace("\"", "'") + "\"}")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User u) throws ClassNotFoundException {
        // Class.forName("org.mariadb.jdbc.Driver");

        if (u == null || u.getUsername() == null || u.getUsername().isBlank()) {
            return Response.status(400)
                    .entity("{\"error\":\"username required\"}")
                    .build();
        }

        String sql = "INSERT INTO users(username, email, password_hash, display_name) " +
                "VALUES(?, ?, 'TEMP', ?)";

        try (Connection cn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getUsername()); // display_name provisional

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    u.setId(keys.getLong(1));
                }
            }

            return Response.ok(u).build();

        } catch (SQLException e) {
            return Response.status(500)
                    .entity("{\"error\":\"" + e.getMessage().replace("\"", "'") + "\"}")
                    .build();
        }
    }
}
