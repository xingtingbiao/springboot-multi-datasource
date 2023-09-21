package com.cfex.jdbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor(staticName = "of")
@Accessors(chain = true)
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long pkCustomer;

    private Short fkSubmarketplace;

    private String virtualContractId;

    private String contractId;

    private String modelName;

    private String underContractId;

    private String meteraccountId;

    private String accountId;

    private String counterpartyA;

    private String counterpartyB;

    private String facilityId;

    private String marketId;

    private String billingCycleId;

    private String timeZone;

    private Date startDate;

    private Date endDate;

    private String serviceStatus;

    private Byte activeFlag;

    private Date activationDate;

    private String activationSource;

    private String activationProcessId;

    private Date deactivationDate;

    private String deactivationSource;

    private String deactivationProcessId;

    private Byte overrideFlag;

    private String overrideValueList;

    private String comment;

    private String fileName;

    private String fileDate;

    private Date creationDate;

    private Date lastModifiedDate;

}