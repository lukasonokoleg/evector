package eu.itreegroup.spark.application.mail;

public class MailHelper {

    /*    public static class MimeMessageBuilder {

            MimeMessage message;

            public MimeMessageBuilder(MimeMessage message) {
                this.message = message;
            }

            void to(String recipient) throws MessagingException {
                addRecipient(RecipientType.TO, message, recipient);
            }

            void to(List<String> recipients) throws MessagingException {
                addRecipients(RecipientType.TO, message, recipients);
            }

            void cc(String recipient) throws MessagingException {
                addRecipient(RecipientType.CC, message, recipient);
            }

            void cc(List<String> recipients) throws MessagingException {
                addRecipients(RecipientType.CC, message, recipients);
            }

            void bcc(String recipient) throws MessagingException {
                addRecipient(RecipientType.BCC, message, recipient);
            }

            void bcc(List<String> recipients) throws MessagingException {
                addRecipients(RecipientType.BCC, message, recipients);
            }

            void from(String from) throws MessagingException {
                Address address = getAddress(from);
                if (null != address) {
                    message.addFrom(new Address[] { address });
                }
            }

            void from(List<String> from) throws MessagingException {
                Address[] addresses = getAddress(from);
                if (null != addresses) {
                    message.addFrom(addresses);
                }
            }

            MimeMessage message() {
                return message;
            }
        }

        public MimeMessageBuilder buildMessage(Session session) throws MessagingException {
            return new MimeMessageBuilder(new MimeMessage(session));
        }

        private static void addRecipients(RecipientType recipientType, Message message, List<String> recipients) throws MessagingException {
            if (null != recipients) {
                for (String recipient : recipients) {
                    addRecipient(recipientType, message, recipient);
                }
            }
        }

        private static void addRecipient(RecipientType recipientType, Message message, String recipient) throws MessagingException {
            if (null != recipient && !recipient.isEmpty()) {
                message.addRecipient(recipientType, getAddress(recipient));
            }
        }

        private static Address getAddress(String email) throws MessagingException {
            if (email == null || email.isEmpty()) {
                return null;
            }
            InternetAddress address = new InternetAddress(email);
            try {
                address.setPersonal(address.getPersonal(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new MessagingException("Cannot encode address", e);
            }
            return address;
        }

        protected static Address[] getAddress(List<String> emails) throws MessagingException {
            Address[] result = null;
            if (null != emails) {
                List<Address> addresses = new ArrayList<Address>();
                for (String s : emails) {
                    if (null != s && !s.isEmpty()) {
                        Address a = getAddress(s);
                        if (null != a) {
                            addresses.add(getAddress(s));
                        }
                    }
                }
                if (addresses.size() != 0) {
                    result = addresses.toArray(new Address[addresses.size()]);
                }
            }
            return result;
        }*/
}
