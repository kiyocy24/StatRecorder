PLUGIN = StatRecorder

.PHONY: build_image
build_image:
	docker run -d -p 25565:25565 --name spigot -e EULA=TRUE nimmis/spigot

.PHONY: deploy
deploy: 
	docker cp ./build/libs/$(PLUGIN)*.jar $(CONTAINER_ID):/minecraft/plugins/

.PHONY: clean
clean:
	docker exec $(CONTAINER_ID) bash -c "rm -rf /minecraft/plugins/$(PLUGIN)*.jar"
	docker exec $(CONTAINER_ID) bash -c "rm -rf /minecraft/plugins/$(PLUGIN)"

