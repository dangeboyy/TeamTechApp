package exception;

public class UnauthorizedUserOperationException extends Exception{
        String message;
        public UnauthorizedUserOperationException(String message){
            this.message=message;
            System.out.println(message);
        }
        @Override
        public String getMessage() {
            return this.message;
        }

}
