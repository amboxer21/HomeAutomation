class CreateLightStatuses < ActiveRecord::Migration
  def up
    create_table :light_statuses do |t|
      t.integer :id
      t.boolean :state

      t.timestamps null: false
    end
  end
    drop_table :light_statuses do |t|
      t.integer :id
      t.boolean :state

      t.timestamps null: false
    end
  def down
  end
end
