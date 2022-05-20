package at.javatraining;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CustomerReactiveRepo {
    @Inject
    MySQLPool client;

    public Multi<Customer> findAll(){
        Uni<RowSet<Row>> uniRowSet = client.query("select * from Customer order by name").execute();
        Multi<Customer> customerMulti = uniRowSet.onItem().transformToMulti((Iterable<Row> set) -> Multi.createFrom().iterable(set))
                .onItem().transform((Row row) -> createCustomerFromRow(row));
        return customerMulti;
    }

   /* public Uni<Customer> insert(Customer customer){
        Uni<RowSet<Row>> execute = client.preparedQuery("INSERT INTO Customer(dateOfBirth,name,status) VALUES ($1, $2, $3) RETURNING id")
                .execute(Tuple.of(customer.getDateOfBirth(), customer.getName(), customer.getStatus().toString()))


    }
*/
    private Customer createCustomerFromRow(Row row){
        return new Customer(row.getLong("id"),
                row.getString("name"),
                row.getLocalDate("dateOfBirth"),
                Customer.Status.valueOf(row.getString("status")));
    }

}
