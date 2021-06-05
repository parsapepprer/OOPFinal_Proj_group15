package model.good;

public class PacketMilk extends Good{
    private static int NUMBER_OF_PACKET_MILK = 0;

    public PacketMilk() {
        super(2, 5, "PacketMilk" + ++NUMBER_OF_PACKET_MILK, 60);
    }
}
