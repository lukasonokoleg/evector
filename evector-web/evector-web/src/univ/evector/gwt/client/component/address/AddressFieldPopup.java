package univ.evector.gwt.client.component.address;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.client.helper.ValueProvider;
import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.SuggestField;
import lt.jmsys.spark.gwt.client.ui.form.field.SuggestField.SuggestValueProvider;
import lt.jmsys.spark.gwt.client.ui.form.field.TextField;
import lt.jmsys.spark.gwt.client.ui.form.validation.ValidationHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.Address;
import univ.evector.beans.Country;
import univ.evector.beans.MaxLengthDomain;
import univ.evector.beans.Municipality;
import univ.evector.beans.Residence;
import univ.evector.beans.Street;
import univ.evector.gwt.client.component.address.resource.AddressConstants;
import univ.evector.shared.formatter.StringFormatter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TheDialogBox;
import com.google.gwt.user.client.ui.UIObject;

public class AddressFieldPopup extends BaseFormView<Address> {

    private static final String COUNTRY_CODE_LT = "LTU";
    private static final AddressConstants C = GWT.create(AddressConstants.class);

    private boolean strictAddressInput;
    private boolean required;

    private TheDialogBox popup = new TheDialogBox(true);

    private Button okButton = ButtonHelper.getInstance().createOkButton(C.btnOk());
    private Button closeButton = ButtonHelper.getInstance().createCloseButton(C.btnClose());

    private CountryOracle countryOracle = new CountryOracle();
    private SuggestField<Country> countryField = new SuggestField<>(countryOracle);

    private MunicipalityOracle municipalityOracle = new MunicipalityOracle(new ValueProvider<String>() {

        @Override
        public String provideValue() {
            Country v = countryField.getValue();
            return null != v ? v.getCnt_iso_code() : null;
        }
    });
    private SuggestField<Municipality> municipalityField = new SuggestField<>(municipalityOracle);

    private ResidenceOracle residenceOracle = new ResidenceOracle(new ValueProvider<Long>() {

        @Override
        public Long provideValue() {
            Municipality v = municipalityField.getValue();
            return null != v ? v.getMncp_id() : null;
        }
    });
    private SuggestField<Residence> residenceField = new SuggestField<>(residenceOracle);

    private StreetOracle streetOracle = new StreetOracle(new ValueProvider<Long>() {

        @Override
        public Long provideValue() {
            Residence v = residenceField.getValue();
            return null != v ? v.getRsd_id() : null;
        }
    });
    private SuggestField<Street> streetField = new SuggestField<>(streetOracle);

    private TextField buildingField = new TextField(C.labelBuildingNumber(), false, MaxLengthDomain.ADDRESS_BUILDING_NO);
    private TextField houseField = new TextField(C.labelHouseNumber(), false, MaxLengthDomain.ADDRESS_HOUSE_NO);
    private TextField roomField = new TextField(C.labelRoomNumber(), false, MaxLengthDomain.ADDRESS_ROOM_NO);

    private FlexTable table = new FlexTable();

    public AddressFieldPopup() {

        popup.setText(C.captionAddress());
        popup.setAutoHideOnHistoryEventsEnabled(true);

        countryField.setLabelText(C.labelCountry());
        municipalityField.setLabelText(C.labelMunicipality());
        residenceField.setLabelText(C.labelResidence());
        streetField.setLabelText(C.labelStreet());

        municipalityField.setMaxLenght(MaxLengthDomain.ADDRESS_MUNICIPALITY);
        residenceField.setMaxLenght(MaxLengthDomain.ADDRESS_RESIDENCE);
        streetField.setMaxLenght(MaxLengthDomain.ADDRESS_STREET);

        buildingField.setUpperCaseEnabled(true);
        houseField.setUpperCaseEnabled(true);

        attachValueChangeHandlers();

        int row = 0;
        table.setWidget(row, 0, countryField.getLabelWidget());
        table.setWidget(row, 1, countryField);

        row++;
        table.setWidget(row, 0, municipalityField.getLabelWidget());
        table.setWidget(row, 1, municipalityField);

        row++;
        table.setWidget(row, 0, residenceField.getLabelWidget());
        table.setWidget(row, 1, residenceField);

        row++;
        table.setWidget(row, 0, streetField.getLabelWidget());
        table.setWidget(row, 1, streetField);

        row++;
        table.setWidget(row, 0, buildingField.getLabelWidget());
        table.setWidget(row, 1, buildingField);

        row++;
        table.setWidget(row, 0, houseField.getLabelWidget());
        table.setWidget(row, 1, houseField);

        row++;
        table.setWidget(row, 0, roomField.getLabelWidget());
        table.setWidget(row, 1, roomField);

        getBodyContainer().add(table);

        getButtonBar().addLeft(okButton);
        getButtonBar().addLeft(closeButton);

        popup.add(this);

        getCloseClickSource().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                popup.hide();
            }
        });

    }

    @Override
    public void setFormValue(Address value) {
        value = null != value ? value : newValue();
        countryField.setValue(value.getCountry());
        municipalityField.setValue(value.getMunicipality());
        residenceField.setValue(value.getResidence());
        streetField.setValue(value.getStreet());
        buildingField.setValue(value.getBuilding_no());
        houseField.setValue(value.getHouse_no());
        roomField.setValue(value.getRoom_no());
    }

    @Override
    public void getValue(Address value) {
        value.setCountry(countryField.getValue());
        value.setMunicipality(municipalityField.getValue());
        value.setResidence(residenceField.getValue());
        value.setStreet(streetField.getValue());
        value.setBuilding_no(buildingField.getValue());
        value.setHouse_no(houseField.getValue());
        value.setRoom_no(roomField.getValue());
    }

    @Override
    public Address newValue() {
        Address address = new Address();
        address.setCountry(new Country(COUNTRY_CODE_LT, C.msgCountryLithuania()));
        return address;
    }

    @Override
    public void defaultFocus() {
        if (null == countryField.getValue()) {
            countryField.setFocus(true);
        } else {
            municipalityField.setFocus(true);
        }
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean valid = true;
        valid = valid & ValidationHelper.validateField(countryField, container);
        valid = valid & ValidationHelper.validateField(municipalityField, container);
        valid = valid & ValidationHelper.validateField(residenceField, container);
        valid = valid & ValidationHelper.validateField(streetField, container);
        valid = valid & ValidationHelper.validateField(buildingField, container);
        valid = valid & ValidationHelper.validateField(houseField, container);
        valid = valid & ValidationHelper.validateField(roomField, container);
        return valid;
    }

    public void show(UIObject uiObject) {
        getMessageContainer().clear();
        popup.showRelativeTo(uiObject);
    }

    protected void updateFormItems() {
        updateRequired();
        if (isStrictAddressInput()) {
            countryField.setReadOnly(true);
            countryField.setNewValueProvider(null);
            municipalityField.setNewValueProvider(null);
            residenceField.setNewValueProvider(null);
            streetField.setNewValueProvider(null);
        } else {
            countryField.setReadOnly(false);
            countryField.setNewValueProvider(new SuggestValueProvider<Country>() {

                @Override
                public Country toValue(String query) {
                    return new Country(null, StringFormatter.capitalizeFirstWord(query));
                }
            });
            municipalityField.setNewValueProvider(new SuggestValueProvider<Municipality>() {

                @Override
                public Municipality toValue(String query) {
                    return new Municipality(null, StringFormatter.capitalizeFirstWord(query));
                }
            });

            residenceField.setNewValueProvider(new SuggestValueProvider<Residence>() {

                @Override
                public Residence toValue(String query) {
                    return new Residence(null, StringFormatter.capitalizeFirstWord(query));
                }
            });

            streetField.setNewValueProvider(new SuggestValueProvider<Street>() {

                @Override
                public Street toValue(String query) {
                    return new Street(null, StringFormatter.capitalizeFirstWord(query));
                }
            });

        }

    }

    protected void updateRequired() {
        Country c = countryField.getValue();
        boolean lt = c != null && COUNTRY_CODE_LT.equals(c.getCnt_iso_code());
        countryField.setRequired(true);
        municipalityField.setRequired(lt);
        residenceField.setRequired(true);
        streetField.setRequired(lt);
        buildingField.setRequired(false);
        houseField.setRequired(false);
        roomField.setRequired(false);
        updateStreetRequired();
    }

    protected void updateStreetRequired() {
        Country c = countryField.getValue();
        boolean lt = c != null && COUNTRY_CODE_LT.equals(c.getCnt_iso_code());
        Residence r = residenceField.getValue();
        boolean streetRequired = null == r || Boolean.TRUE.equals(r.getRsd_has_streets());
        streetField.setRequired(lt && streetRequired);
    }

    public boolean isStrictAddressInput() {
        return strictAddressInput;
    }

    public void setStrictAddressInput(boolean strictAddressInput) {
        this.strictAddressInput = strictAddressInput;
        updateFormItems();
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
        updateFormItems();
    }

    @Override
    public String getFormCaption() {
        return null;
    }

    public HasClickHandlers getCloseClickSource() {
        return closeButton;
    }

    public HasClickHandlers getOkClickSource() {
        return okButton;
    }

    public void hide() {
        popup.hide();
    }

    protected void attachValueChangeHandlers() {
        countryField.addValueChangeHandler(new ValueChangeHandler<Country>() {

            @Override
            public void onValueChange(ValueChangeEvent<Country> event) {
                municipalityField.setValue(null, true);
                updateRequired();
            }
        });

        municipalityField.addValueChangeHandler(new ValueChangeHandler<Municipality>() {

            @Override
            public void onValueChange(ValueChangeEvent<Municipality> event) {
                residenceField.setValue(null, true);
            }
        });

        residenceField.addValueChangeHandler(new ValueChangeHandler<Residence>() {

            @Override
            public void onValueChange(ValueChangeEvent<Residence> event) {
                updateStreetRequired();
                streetField.setValue(null, true);
            }
        });

        streetField.addValueChangeHandler(new ValueChangeHandler<Street>() {

            @Override
            public void onValueChange(ValueChangeEvent<Street> event) {
                buildingField.setValue(null);
                houseField.setValue(null);
                roomField.setValue(null);
            }
        });
    }

}
