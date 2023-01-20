class Storage {

    StorageType type;
    static int offset;
    int value; // Offset in stack, or Register number

    Storage(StorageType st)  {
        type = st;
        this.offset += 4;
        this.value = offset;
    }

    void print() {
        switch(type) {
            case Stack:
                System.out.println("in stack at offset "+value);
                break;
            case Register:
                System.out.println("in register R"+value);
                break;
        }
    }
}