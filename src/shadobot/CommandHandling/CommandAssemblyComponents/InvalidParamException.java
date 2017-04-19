package shadobot.CommandHandling.CommandAssemblyComponents;

public class InvalidParamException extends Exception {
        private String message;

        public InvalidParamException(String message) {
            super(message);
            this.message = message;
        }

    public InvalidParamException() {
        super();
    }

    public String getErrorMessage() {
            return this.message;
        }
}
