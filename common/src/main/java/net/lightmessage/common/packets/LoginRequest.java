package net.lightmessage.common.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LoginRequest extends Packet {
    public static final int ID = 5;

    String username;
    String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {
    }

    @Override
    Packet read(DataInputStream inputStream) throws IOException {
        int usernameLength = inputStream.readInt();
        username = new String(inputStream.readNBytes(usernameLength), StandardCharsets.UTF_8);
        int passwordLength = inputStream.readInt();
        password = new String(inputStream.readNBytes(passwordLength), StandardCharsets.UTF_8);
        int hashCode = inputStream.readInt();
        if(hashCode!=hashCode()){
            throw new IOException("Non-matching hash value when reading packet");
        }
        return this;
    }

    @Override
    public void write(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(Packet.MAGIC_BYTES);
        outputStream.writeInt(ID);
        outputStream.writeInt(username.length());
        outputStream.write(username.getBytes(StandardCharsets.UTF_8));
        outputStream.writeInt(password.length());
        outputStream.write(password.getBytes(StandardCharsets.UTF_8));
        outputStream.writeInt(hashCode());
        outputStream.flush();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
