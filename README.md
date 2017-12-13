# springboot-consul

#1. Register Service
        // register new service with associated health check
        NewService newService = new NewService();
        newService.setId(id);
        newService.setTags(Collections.singletonList("tiandi_v1"));
        newService.setName(clientName);
        newService.setAddress("http://10.60.38.176");
        newService.setPort(8080);

        NewService.Check serviceCheck = new NewService.Check();
        serviceCheck.setHttp("http://10.60.38.176:8080/health");
        serviceCheck.setInterval("5s");
        serviceCheck.setDeregisterCriticalServiceAfter("1m");
        newService.setCheck(serviceCheck);

        consulClient.agentServiceRegister(newService);

#2. Deregister Service
        List<HealthService> response = consulClient.getHealthServices(clientName,false,null).getValue();
        System.out.println(response.size());
        for(HealthService service : response){
            //创建一个用来剔除无效实例的ConsulClient，连接到无效实例注册的agent
            ConsulClient clearClient = new ConsulClient("202.120.167.198",8500);
            service.getChecks().forEach(check -> {
                if (check.getServiceId().equals(id)){
                    Logger logger = Logger.getLogger(check.getServiceId());
                    logger.info("deregister:{}"+check.getServiceId());
                    clearClient.agentServiceDeregister(check.getServiceId());
                    System.out.println("Deregister success------"+id);
                }
            });
        }
  
#3. Register and Deregister Automatically

    ConsulStartupRunner implements CommandLineRunner{
      @override
      run(){
        consulService.registerService(instanceId);
      }
    }
    
    @Predestory
    public void beforeExit(){
      consulService.deregisterService(instanceId);
    }
