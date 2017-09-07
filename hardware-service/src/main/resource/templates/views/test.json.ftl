<#escape x as x?html>
    {

        <#list oneList as one>
                {
                    "id":"${one.id}",
                    "name":"${one.name}"
                }

        </#list>

    }
</#escape>