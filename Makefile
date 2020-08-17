PLUGIN = StatisticsNotifier

.PHONY: deploy
deploy: 
	docker cp ./build/libs/$(PLUGIN)*.jar $(CONTAINER_ID):/minecraft/plugins/

.PHONY: clean
clean:
	docker exec $(CONTAINER_ID) bash -c "rm -rf /minecraft/plugins/$(PLUGIN)*.jar"
	docker exec $(CONTAINER_ID) bash -c "rm -rf /minecraft/plugins/$(PLUGIN)"

