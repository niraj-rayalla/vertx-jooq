/*
 * This file is generated by jOOQ.
 */
package generated.rx.reactive.guice.tables.pojos;


import generated.rx.reactive.guice.enums.Someenum;
import generated.rx.reactive.guice.tables.interfaces.ISomething;

import io.github.jklingsporn.vertx.jooq.generate.converter.SomeJsonPojo;
import io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;


import static io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo.*;
/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Something implements VertxPojo, ISomething {

    private static final long serialVersionUID = -2121625881;

    private Integer        someid;
    private String         somestring;
    private Long           somehugenumber;
    private Short          somesmallnumber;
    private Integer        someregularnumber;
    private Double         somedouble;
    private Someenum       someenum;
    private JsonObject     somejsonobject;
    private SomeJsonPojo   somecustomjsonobject;
    private JsonArray      somejsonarray;
    private JsonObject     somevertxjsonobject;
    private LocalTime      sometime;
    private LocalDate      somedate;
    private LocalDateTime  sometimestamp;
    private OffsetDateTime sometimestampwithtz;
    private byte[]         somebytea;
    private List<String>   somestringaslist;

    public Something() {}

    public Something(ISomething value) {
        this.someid = value.getSomeid();
        this.somestring = value.getSomestring();
        this.somehugenumber = value.getSomehugenumber();
        this.somesmallnumber = value.getSomesmallnumber();
        this.someregularnumber = value.getSomeregularnumber();
        this.somedouble = value.getSomedouble();
        this.someenum = value.getSomeenum();
        this.somejsonobject = value.getSomejsonobject();
        this.somecustomjsonobject = value.getSomecustomjsonobject();
        this.somejsonarray = value.getSomejsonarray();
        this.somevertxjsonobject = value.getSomevertxjsonobject();
        this.sometime = value.getSometime();
        this.somedate = value.getSomedate();
        this.sometimestamp = value.getSometimestamp();
        this.sometimestampwithtz = value.getSometimestampwithtz();
        this.somebytea = value.getSomebytea();
        this.somestringaslist = value.getSomestringaslist();
    }

    public Something(
        Integer        someid,
        String         somestring,
        Long           somehugenumber,
        Short          somesmallnumber,
        Integer        someregularnumber,
        Double         somedouble,
        Someenum       someenum,
        JsonObject     somejsonobject,
        SomeJsonPojo   somecustomjsonobject,
        JsonArray      somejsonarray,
        JsonObject     somevertxjsonobject,
        LocalTime      sometime,
        LocalDate      somedate,
        LocalDateTime  sometimestamp,
        OffsetDateTime sometimestampwithtz,
        byte[]         somebytea,
        List<String>   somestringaslist
    ) {
        this.someid = someid;
        this.somestring = somestring;
        this.somehugenumber = somehugenumber;
        this.somesmallnumber = somesmallnumber;
        this.someregularnumber = someregularnumber;
        this.somedouble = somedouble;
        this.someenum = someenum;
        this.somejsonobject = somejsonobject;
        this.somecustomjsonobject = somecustomjsonobject;
        this.somejsonarray = somejsonarray;
        this.somevertxjsonobject = somevertxjsonobject;
        this.sometime = sometime;
        this.somedate = somedate;
        this.sometimestamp = sometimestamp;
        this.sometimestampwithtz = sometimestampwithtz;
        this.somebytea = somebytea;
        this.somestringaslist = somestringaslist;
    }

    public Something(io.vertx.core.json.JsonObject json) {
        this();
        fromJson(json);
    }

    @Override
    public Integer getSomeid() {
        return this.someid;
    }

    @Override
    public Something setSomeid(Integer someid) {
        this.someid = someid;
        return this;
    }

    @Override
    public String getSomestring() {
        return this.somestring;
    }

    @Override
    public Something setSomestring(String somestring) {
        this.somestring = somestring;
        return this;
    }

    @Override
    public Long getSomehugenumber() {
        return this.somehugenumber;
    }

    @Override
    public Something setSomehugenumber(Long somehugenumber) {
        this.somehugenumber = somehugenumber;
        return this;
    }

    @Override
    public Short getSomesmallnumber() {
        return this.somesmallnumber;
    }

    @Override
    public Something setSomesmallnumber(Short somesmallnumber) {
        this.somesmallnumber = somesmallnumber;
        return this;
    }

    @Override
    public Integer getSomeregularnumber() {
        return this.someregularnumber;
    }

    @Override
    public Something setSomeregularnumber(Integer someregularnumber) {
        this.someregularnumber = someregularnumber;
        return this;
    }

    @Override
    public Double getSomedouble() {
        return this.somedouble;
    }

    @Override
    public Something setSomedouble(Double somedouble) {
        this.somedouble = somedouble;
        return this;
    }

    @Override
    public Someenum getSomeenum() {
        return this.someenum;
    }

    @Override
    public Something setSomeenum(Someenum someenum) {
        this.someenum = someenum;
        return this;
    }

    @Override
    public JsonObject getSomejsonobject() {
        return this.somejsonobject;
    }

    @Override
    public Something setSomejsonobject(JsonObject somejsonobject) {
        this.somejsonobject = somejsonobject;
        return this;
    }

    @Override
    public SomeJsonPojo getSomecustomjsonobject() {
        return this.somecustomjsonobject;
    }

    @Override
    public Something setSomecustomjsonobject(SomeJsonPojo somecustomjsonobject) {
        this.somecustomjsonobject = somecustomjsonobject;
        return this;
    }

    @Override
    public JsonArray getSomejsonarray() {
        return this.somejsonarray;
    }

    @Override
    public Something setSomejsonarray(JsonArray somejsonarray) {
        this.somejsonarray = somejsonarray;
        return this;
    }

    @Override
    public JsonObject getSomevertxjsonobject() {
        return this.somevertxjsonobject;
    }

    @Override
    public Something setSomevertxjsonobject(JsonObject somevertxjsonobject) {
        this.somevertxjsonobject = somevertxjsonobject;
        return this;
    }

    @Override
    public LocalTime getSometime() {
        return this.sometime;
    }

    @Override
    public Something setSometime(LocalTime sometime) {
        this.sometime = sometime;
        return this;
    }

    @Override
    public LocalDate getSomedate() {
        return this.somedate;
    }

    @Override
    public Something setSomedate(LocalDate somedate) {
        this.somedate = somedate;
        return this;
    }

    @Override
    public LocalDateTime getSometimestamp() {
        return this.sometimestamp;
    }

    @Override
    public Something setSometimestamp(LocalDateTime sometimestamp) {
        this.sometimestamp = sometimestamp;
        return this;
    }

    @Override
    public OffsetDateTime getSometimestampwithtz() {
        return this.sometimestampwithtz;
    }

    @Override
    public Something setSometimestampwithtz(OffsetDateTime sometimestampwithtz) {
        this.sometimestampwithtz = sometimestampwithtz;
        return this;
    }

    @Override
    public byte[] getSomebytea() {
        return this.somebytea;
    }

    @Override
    public Something setSomebytea(byte[] somebytea) {
        this.somebytea = somebytea;
        return this;
    }

    @Override
    public List<String> getSomestringaslist() {
        return this.somestringaslist;
    }

    @Override
    public Something setSomestringaslist(List<String> somestringaslist) {
        this.somestringaslist = somestringaslist;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Something other = (Something) obj;
        if (someid == null) {
            if (other.someid != null)
                return false;
        }
        else if (!someid.equals(other.someid))
            return false;
        if (somestring == null) {
            if (other.somestring != null)
                return false;
        }
        else if (!somestring.equals(other.somestring))
            return false;
        if (somehugenumber == null) {
            if (other.somehugenumber != null)
                return false;
        }
        else if (!somehugenumber.equals(other.somehugenumber))
            return false;
        if (somesmallnumber == null) {
            if (other.somesmallnumber != null)
                return false;
        }
        else if (!somesmallnumber.equals(other.somesmallnumber))
            return false;
        if (someregularnumber == null) {
            if (other.someregularnumber != null)
                return false;
        }
        else if (!someregularnumber.equals(other.someregularnumber))
            return false;
        if (somedouble == null) {
            if (other.somedouble != null)
                return false;
        }
        else if (!somedouble.equals(other.somedouble))
            return false;
        if (someenum == null) {
            if (other.someenum != null)
                return false;
        }
        else if (!someenum.equals(other.someenum))
            return false;
        if (somejsonobject == null) {
            if (other.somejsonobject != null)
                return false;
        }
        else if (!somejsonobject.equals(other.somejsonobject))
            return false;
        if (somecustomjsonobject == null) {
            if (other.somecustomjsonobject != null)
                return false;
        }
        else if (!somecustomjsonobject.equals(other.somecustomjsonobject))
            return false;
        if (somejsonarray == null) {
            if (other.somejsonarray != null)
                return false;
        }
        else if (!somejsonarray.equals(other.somejsonarray))
            return false;
        if (somevertxjsonobject == null) {
            if (other.somevertxjsonobject != null)
                return false;
        }
        else if (!somevertxjsonobject.equals(other.somevertxjsonobject))
            return false;
        if (sometime == null) {
            if (other.sometime != null)
                return false;
        }
        else if (!sometime.equals(other.sometime))
            return false;
        if (somedate == null) {
            if (other.somedate != null)
                return false;
        }
        else if (!somedate.equals(other.somedate))
            return false;
        if (sometimestamp == null) {
            if (other.sometimestamp != null)
                return false;
        }
        else if (!sometimestamp.equals(other.sometimestamp))
            return false;
        if (sometimestampwithtz == null) {
            if (other.sometimestampwithtz != null)
                return false;
        }
        else if (!sometimestampwithtz.equals(other.sometimestampwithtz))
            return false;
        if (somebytea == null) {
            if (other.somebytea != null)
                return false;
        }
        else if (!Arrays.equals(somebytea, other.somebytea))
            return false;
        if (somestringaslist == null) {
            if (other.somestringaslist != null)
                return false;
        }
        else if (!somestringaslist.equals(other.somestringaslist))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.someid == null) ? 0 : this.someid.hashCode());
        result = prime * result + ((this.somestring == null) ? 0 : this.somestring.hashCode());
        result = prime * result + ((this.somehugenumber == null) ? 0 : this.somehugenumber.hashCode());
        result = prime * result + ((this.somesmallnumber == null) ? 0 : this.somesmallnumber.hashCode());
        result = prime * result + ((this.someregularnumber == null) ? 0 : this.someregularnumber.hashCode());
        result = prime * result + ((this.somedouble == null) ? 0 : this.somedouble.hashCode());
        result = prime * result + ((this.someenum == null) ? 0 : this.someenum.hashCode());
        result = prime * result + ((this.somejsonobject == null) ? 0 : this.somejsonobject.hashCode());
        result = prime * result + ((this.somecustomjsonobject == null) ? 0 : this.somecustomjsonobject.hashCode());
        result = prime * result + ((this.somejsonarray == null) ? 0 : this.somejsonarray.hashCode());
        result = prime * result + ((this.somevertxjsonobject == null) ? 0 : this.somevertxjsonobject.hashCode());
        result = prime * result + ((this.sometime == null) ? 0 : this.sometime.hashCode());
        result = prime * result + ((this.somedate == null) ? 0 : this.somedate.hashCode());
        result = prime * result + ((this.sometimestamp == null) ? 0 : this.sometimestamp.hashCode());
        result = prime * result + ((this.sometimestampwithtz == null) ? 0 : this.sometimestampwithtz.hashCode());
        result = prime * result + ((this.somebytea == null) ? 0 : Arrays.hashCode(this.somebytea));
        result = prime * result + ((this.somestringaslist == null) ? 0 : this.somestringaslist.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Something (");

        sb.append(someid);
        sb.append(", ").append(somestring);
        sb.append(", ").append(somehugenumber);
        sb.append(", ").append(somesmallnumber);
        sb.append(", ").append(someregularnumber);
        sb.append(", ").append(somedouble);
        sb.append(", ").append(someenum);
        sb.append(", ").append(somejsonobject);
        sb.append(", ").append(somecustomjsonobject);
        sb.append(", ").append(somejsonarray);
        sb.append(", ").append(somevertxjsonobject);
        sb.append(", ").append(sometime);
        sb.append(", ").append(somedate);
        sb.append(", ").append(sometimestamp);
        sb.append(", ").append(sometimestampwithtz);
        sb.append(", ").append("[binary...]");
        sb.append(", ").append(somestringaslist);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(ISomething from) {
        setSomeid(from.getSomeid());
        setSomestring(from.getSomestring());
        setSomehugenumber(from.getSomehugenumber());
        setSomesmallnumber(from.getSomesmallnumber());
        setSomeregularnumber(from.getSomeregularnumber());
        setSomedouble(from.getSomedouble());
        setSomeenum(from.getSomeenum());
        setSomejsonobject(from.getSomejsonobject());
        setSomecustomjsonobject(from.getSomecustomjsonobject());
        setSomejsonarray(from.getSomejsonarray());
        setSomevertxjsonobject(from.getSomevertxjsonobject());
        setSometime(from.getSometime());
        setSomedate(from.getSomedate());
        setSometimestamp(from.getSometimestamp());
        setSometimestampwithtz(from.getSometimestampwithtz());
        setSomebytea(from.getSomebytea());
        setSomestringaslist(from.getSomestringaslist());
    }

    @Override
    public <E extends ISomething> E into(E into) {
        into.from(this);
        return into;
    }
}
