package display;

public class HDMIAdapter implements HDMI {
    private final VGA vga;

    public HDMIAdapter(VGA vga) {
        this.vga = vga;
    }

    @Override
    public void display(char[] data) {
        vga.show(new String(data));
    }
}