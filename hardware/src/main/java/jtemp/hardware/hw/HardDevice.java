package jtemp.hardware.hw;

/**
 * Created by ZMS on 2016/11/24.
 */

public class HardDevice {

    private String serialNumber;

    private String description;

    private int id;

    private int location;

    private DeviceType type;

    private boolean readable;

    private boolean writeable;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("设备：type:%s,serialNumber:%s,id:%s,location:%s,description:%s", type.getName(), serialNumber, id, location, description);
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public boolean isWriteable() {
        return writeable;
    }

    public void setWriteable(boolean writeable) {
        this.writeable = writeable;
    }
}
