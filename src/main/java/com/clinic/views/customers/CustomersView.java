package com.clinic.views.customers;

import com.clinic.domain.dto.CustomerDto;
import com.clinic.mapper.CustomerMapper;
import com.clinic.service.CustomerDBService;
import com.clinic.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = MainView.class)
@PageTitle("Customers")
@CssImport("./views/customers/customers-view.css")
public class CustomersView extends Div {

    private CustomerDBService customerDBService;
    private CustomerMapper customerMapper;
    private Grid<CustomerDto> grid = new Grid<>(CustomerDto.class);
    private TextField filterText = new TextField();

    private TextField id = new TextField("Id");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField pesel = new TextField("Pesel");
    private TextField email = new TextField("E-mail");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<CustomerDto> binder = new Binder<>(CustomerDto.class);

    public CustomersView(@Autowired CustomerDBService customerDBService, @Autowired CustomerMapper customerMapper) {
        this.customerDBService = customerDBService;
        this.customerMapper = customerMapper;

        addClassName("customers-view");
        updateList();

        grid.setColumns("firstName", "lastName", "pesel","email");
        grid.asSingleSelect().addValueChangeListener(event ->
                binder.setBean(grid.asSingleSelect().getValue()));

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        binder.forField(id).bind(c -> Long.toString(c.getId()),(c,s) -> c.setId(Long.parseLong(s)));
        binder.forField(firstName).bind(CustomerDto::getFirstName,CustomerDto::setFirstName);
        binder.forField(lastName).bind(CustomerDto::getLastName,CustomerDto::setLastName);
        binder.forField(pesel).bind(CustomerDto::getPesel,CustomerDto::setPesel);
        binder.forField(email).bind(CustomerDto::getEmail,CustomerDto::setEmail);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        FormLayout form = new FormLayout(firstName, lastName, pesel, email, buttons);
        HorizontalLayout mainContent = new HorizontalLayout(grid,form);
        add(filterText,mainContent);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
    }

    public void updateList() {
        grid.setItems(customerMapper.mapToCustomerDtoList(customerDBService.getAllCustomers(filterText.getValue())));
    }

    private void save() {
        CustomerDto customerDto = binder.getBean();
        if(customerDto==null) {
            customerDto = new CustomerDto(firstName.getValue(),lastName.getValue(),pesel.getValue(),email.getValue());
        }
        customerDBService.saveCustomer(customerMapper.mapToCustomer(customerDto));
        updateList();
    }

    private void delete() {
        CustomerDto customerDto = binder.getBean();
        customerDBService.deleteCustomer(customerDto.getId());
        updateList();
    }
}
